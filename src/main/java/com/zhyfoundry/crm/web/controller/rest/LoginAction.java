package com.zhyfoundry.crm.web.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginAction {

    @RequestMapping(value = "/rest/login/{user}", method = RequestMethod.GET)
    public void login(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            @PathVariable("user") final String user,
            final ModelMap modelMap) throws Exception {
        System.out.println(user);
        resp.getOutputStream().write(user.getBytes());
    }

    @RequestMapping(value = "/rest/login", method = RequestMethod.GET)
    public void loginNoUserInput(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
        login(req, resp, "no user input!", modelMap);
    }
}
