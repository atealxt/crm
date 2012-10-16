package com.zhyfoundry.crm.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhyfoundry.crm.entity.Father;
import com.zhyfoundry.crm.service.FlushService;

@Controller
public class TestFlush {

    @Autowired
    @Qualifier("FlushService")
    protected FlushService flushService;

    @RequestMapping("/testFlush.action")
    public void hello(HttpServletResponse resp) {

        Father f = new Father();
        f.setName("spring");
        flushService.execute(f);

        try {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().write("test success");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
