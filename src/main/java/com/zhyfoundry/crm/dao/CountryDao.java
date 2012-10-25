package com.zhyfoundry.crm.dao;

import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.entity.Country;

public interface CountryDao extends BaseDao<Country, Integer> {

    Country findByName(String name);
}
