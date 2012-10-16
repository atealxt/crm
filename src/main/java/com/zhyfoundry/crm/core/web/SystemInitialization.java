package com.zhyfoundry.crm.core.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhyfoundry.crm.core.DIManager;
import com.zhyfoundry.crm.service.TeacherService;

public class SystemInitialization extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static Log logger = LogFactory.getLog(SystemInitialization.class);

    protected TeacherService service;

    @Override
    public final void init() throws ServletException {
        logger.info("项目启动设置 开始");
        service = DIManager.getBean("TeacherService");

        // 测试事务的生命周期
        service.getTeathers();

        service.getTeathers2().get(0).getStudents();

        logger.info("项目启动设置 完毕");
    }
}
