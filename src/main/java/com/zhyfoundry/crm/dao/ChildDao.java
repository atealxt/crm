package com.zhyfoundry.crm.dao;

import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.entity.Child;

public interface ChildDao extends BaseDao<Child, Integer> {

    Child findByName(String name);
}
