package com.zhyfoundry.crm.dao.jpa;

import org.springframework.stereotype.Repository;

import com.zhyfoundry.crm.core.dao.BaseDaoJpa;
import com.zhyfoundry.crm.dao.AdminDao;
import com.zhyfoundry.crm.entity.Administrator;

@Repository
public class AdminDaoJpa extends BaseDaoJpa<Administrator, Integer> implements AdminDao {

	public AdminDaoJpa() {
		super(Administrator.class);
	}

	@Override
	public boolean exist(final String username) {
		return count("username = ?", username) != 0;
	}

	@Override
	public boolean exist(final String username, final String password) {
		return count("username = ? and password = ?", username, password) != 0;
	}
}
