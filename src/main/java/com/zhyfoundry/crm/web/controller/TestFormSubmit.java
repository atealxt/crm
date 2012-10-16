package com.zhyfoundry.crm.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhyfoundry.crm.web.vo.ChildVo;

@Controller
public class TestFormSubmit {

    @Autowired
    @Qualifier("validator")
    private Validator validator;

    @RequestMapping(value = "/test-form.action", method = RequestMethod.GET)
    public ModelMap testforminit() throws IOException {
        return new ModelMap("child", new ChildVo());
    }

    @RequestMapping(value = "/test-form.action", method = RequestMethod.POST)
    public String testform(@ModelAttribute("child") final ChildVo child, final BindingResult result,// 这个参数必须往前放，要不然报错。。。。。
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap model) throws IOException {
        System.out.println("child: " + child);

        validator.validate(child, result);
        if (result.hasErrors()) {
            System.out.println(result);
            return "test-form";
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(child.toString());
        return null;
    }
}
