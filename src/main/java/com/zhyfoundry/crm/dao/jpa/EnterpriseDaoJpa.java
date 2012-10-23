package com.zhyfoundry.crm.dao.jpa;

import org.springframework.stereotype.Repository;

import com.zhyfoundry.crm.core.dao.BaseDaoJpa;
import com.zhyfoundry.crm.dao.EnterpriseDao;
import com.zhyfoundry.crm.entity.Enterprise;

@Repository
public class EnterpriseDaoJpa extends BaseDaoJpa<Enterprise, Integer> implements EnterpriseDao {

    public EnterpriseDaoJpa() {
        super(Enterprise.class);
    }
}
