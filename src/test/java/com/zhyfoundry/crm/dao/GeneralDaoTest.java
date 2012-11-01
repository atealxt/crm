package com.zhyfoundry.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhyfoundry.crm.TestBase;

public class GeneralDaoTest extends TestBase {

	@Autowired
	private GeneralDao generalDao;

	@Override
	public void execute() throws Exception {
		generalDao.count("from Administrator where username != ?", "hahaha");
	}
}
