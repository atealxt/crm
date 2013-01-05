package com.zhyfoundry.crm.web.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.exception.SystemException;
import org.claros.intouch.profiling.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zhyfoundry.crm.entity.Administrator;
import com.zhyfoundry.crm.service.AdminService;
import com.zhyfoundry.crm.utils.CommonUtils;
import com.zhyfoundry.crm.web.BaseController;

@Controller
public class AdminController extends BaseController {

    public static final String ADMIN_INDEX = "admin/index";
    public static final String ADMIN_LOGIN = "admin/login";
    public static final String LOGGEDIN = "LOGGEDIN";
    public static final String OK = "OK";

    @RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
    public ModelAndView admin(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap model) throws IOException {
        if (OK.equals(req.getSession().getAttribute(LOGGEDIN))) {
            return new ModelAndView(ADMIN_INDEX, null);
        }
        ModelMap modelMap = new ModelMap("admin", new Administrator());
        return new ModelAndView(ADMIN_LOGIN, modelMap);
    }

    @RequestMapping(value = { "/admin" }, method = RequestMethod.POST)
    public String login(@ModelAttribute("admin") final Administrator admin, final BindingResult result, final HttpServletRequest req, final HttpServletResponse resp)
            throws IOException, NoSuchAlgorithmException, SystemException {

        validator.validate(admin, result);
        if (result.hasErrors()) {
            return ADMIN_LOGIN;
        }

        if (adminService.notInWhiteList(admin.getUsername())) {
        	resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        	return null;
        }

        Administrator adminFromDb = adminService.find(admin.getUsername(), CommonUtils.md5Hex(admin.getPassword()));
        if (adminFromDb == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        // login to Mail Server
        boolean loginToMailServer = LoginService.login(admin.getUsername(), admin.getPassword(), req);
        if (!loginToMailServer) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        initLogin(req, adminFromDb);

        return ADMIN_INDEX;
    }

	public static void initLogin(HttpServletRequest req, Administrator admin) {
        req.getSession().setAttribute(LOGGEDIN, OK);
    }

    @Autowired
    AdminService adminService;

    @Autowired
    @Qualifier("validator")
    private Validator validator;
}
