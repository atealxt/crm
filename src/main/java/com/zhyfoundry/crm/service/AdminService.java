package com.zhyfoundry.crm.service;

import java.security.NoSuchAlgorithmException;

import org.claros.commons.auth.models.AuthProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.core.service.BaseServiceImpl;
import com.zhyfoundry.crm.dao.AdminDao;
import com.zhyfoundry.crm.entity.Administrator;
import com.zhyfoundry.crm.utils.CommonUtils;

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

	@Transactional
	public boolean loginFromMail(AuthProfile auth) {
		if (adminDao.exist(auth.getUsername())) {
			try {
				return adminDao.exist(auth.getUsername(), CommonUtils.md5Hex(auth.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
		save(new Administrator(auth.getUsername(), auth.getPassword()));
		return true;
	}
}
