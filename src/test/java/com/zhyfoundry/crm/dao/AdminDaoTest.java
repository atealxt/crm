package com.zhyfoundry.crm.dao;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhyfoundry.crm.TestBase;
import com.zhyfoundry.crm.environment.DBMaker;

public class AdminDaoTest extends TestBase {

	@Autowired
	private AdminDao adminDao;

	@Override
	public void execute() throws Exception {
		Assert.assertFalse(adminDao.exist("hahaha"));
		Assert.assertTrue(adminDao.exist(DBMaker.USERNAME_ADMIN));
	}
}
