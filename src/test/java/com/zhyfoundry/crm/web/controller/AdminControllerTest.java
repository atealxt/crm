package com.zhyfoundry.crm.web.controller;

import java.util.Collections;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.MapBindingResult;

import com.zhyfoundry.crm.TestBase;
import com.zhyfoundry.crm.dao.AdminDao;
import com.zhyfoundry.crm.entity.Administrator;
import com.zhyfoundry.crm.environment.DBMaker;

public class AdminControllerTest extends TestBase {

	@Autowired
	protected AdminController controller;
	@Autowired
	protected AdminDao adminDao;

	@Override
	public void execute() throws Exception {
		Assert.assertEquals(AdminController.ADMIN_INDEX,//
				controller.login(new Administrator(DBMaker.USERNAME_ADMIN, DBMaker.PASSWORD_ADMIN), new MapBindingResult(Collections.emptyMap(), "admin"), request, response));
	}
}
