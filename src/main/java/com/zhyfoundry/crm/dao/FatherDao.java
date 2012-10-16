package com.zhyfoundry.crm.dao;

import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.entity.Father;

public interface FatherDao extends BaseDao<Father, Integer> {

    Father findByName(String name);
}
