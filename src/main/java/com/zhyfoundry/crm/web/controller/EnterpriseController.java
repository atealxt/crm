package com.zhyfoundry.crm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhyfoundry.crm.web.PagingController;

@Controller
public class EnterpriseController  extends PagingController {

    @RequestMapping(value = "/admin/enterprise", method = RequestMethod.GET)
    public String list(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
    	// TODO
    	return "admin/enterprise/list";
    }

    @RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.GET)
    public String view(
    		@PathVariable final Integer id,
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
    	// TODO
    	return null;
    }

    @RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.POST)
    public String add(
    		@PathVariable final Integer id,
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
    	// TODO
    	return null;
    }

    @RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.PUT)
    public String edit(
    		@PathVariable final Integer id,
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
    	// TODO
    	return null;
    }

    @RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.DELETE)
    public String delete(
    		@PathVariable final Integer id,
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
    	// TODO
    	return null;
    }

	@Override
	protected int getDefaultPageSize() {
		return 50;
	}

	@Override
	protected int getCountLimitation() {
		return 500;
	}
}
