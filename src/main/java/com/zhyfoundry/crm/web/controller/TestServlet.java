package com.zhyfoundry.crm.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhyfoundry.crm.core.DIManager;
import com.zhyfoundry.crm.dao.FatherDao;

public class TestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FatherDao fatherDao = (FatherDao) DIManager.getBean("FatherDao");
        response.getWriter().print(fatherDao.findByName("tom"));
    }
}
