package com.zhyfoundry.crm.service;

import java.util.List;

import org.claros.commons.auth.models.AuthProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.core.service.BaseServiceImpl;
import com.zhyfoundry.crm.dao.AdminDao;
import com.zhyfoundry.crm.entity.Administrator;

@Service
public class AdminService extends BaseServiceImpl<Administrator, Integer> {

	@Autowired
	protected AdminDao adminDao;

	@Override
	protected BaseDao<Administrator, Integer> getDao() {
		return adminDao;
	}

	public boolean login(String username, String password) {
		return adminDao.exist(username, password);
	}

	public Administrator find(String username, String password) {
		List<Administrator> list = find("from Administrator where username = ? and password = ?", username, password);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Transactional
	public Administrator loginFromMail(AuthProfile auth) {
		if (adminDao.exist(auth.getUsername())) {
			return find(auth.getUsername(), auth.getPassword());
		}
		return save(new Administrator(auth.getUsername(), auth.getPassword()));
	}
}
