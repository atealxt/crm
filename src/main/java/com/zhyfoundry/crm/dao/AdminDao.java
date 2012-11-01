package com.zhyfoundry.crm.dao;

import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.entity.Administrator;

public interface AdminDao extends BaseDao<Administrator, Integer> {

	boolean exist(String username);

	boolean exist(String username, String password);
}
