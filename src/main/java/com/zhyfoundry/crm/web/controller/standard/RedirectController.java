package com.zhyfoundry.crm.web.controller.standard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class RedirectController implements Controller {

    @Override
    public ModelAndView handleRequest(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {

        return new ModelAndView("redirect:/response.action?p1=a&p2=b");
//        return new ModelAndView("redirect:/response.action?p1=a&p2=b&donotuseGzip=1");
    }

}
