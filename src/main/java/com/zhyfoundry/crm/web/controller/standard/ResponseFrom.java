package com.zhyfoundry.crm.web.controller.standard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ResponseFrom implements Controller {

    @Override
    public ModelAndView handleRequest(final HttpServletRequest arg0, final HttpServletResponse arg1) throws Exception {
        return new ModelAndView(new ResponseTo());
    }

}
