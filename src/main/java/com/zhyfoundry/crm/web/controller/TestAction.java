package com.zhyfoundry.crm.web.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhyfoundry.crm.entity.Father;
import com.zhyfoundry.crm.service.FatherService;
import com.zhyfoundry.crm.service.TeacherService;

@Controller
public class TestAction {

    @Autowired
    @Qualifier("FatherService")
    private FatherService fatherService;
    @Autowired
    @Qualifier("TeacherService")
    private TeacherService teacherService;
    @Resource
    protected MessageSource messageSource;

    @RequestMapping("/test.action")
    public String test(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap model)
            throws IOException {
        final Father f = fatherService.getFather();
        model.addAttribute("Father", f);

        final MessageSourceAccessor text = new MessageSourceAccessor(messageSource, req.getLocale());
        model.addAttribute("dataList",//
                           Arrays.asList("a", "啊", "<a href=\"aa\">&1１</a>", text.getMessage("helloworld")));
        model.addAttribute("dt", new Date());
        model.addAttribute("num", 1234.56789);
        model.addAttribute("map", ArrayUtils.toMap(new Object[][] { //
                { "key1", "value1" }, { "key2", "value2" }, { "key3", "value3" } }//
                ));

        // model.addAttribute("Teachers", teacherService.getTeathers());
        model.addAttribute("Teachers", teacherService.getTeathers2());

        return "test";
    }
}
