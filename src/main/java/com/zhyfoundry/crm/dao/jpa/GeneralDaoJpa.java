package com.zhyfoundry.crm.dao.jpa;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.zhyfoundry.crm.core.dao.BaseDaoJpa;
import com.zhyfoundry.crm.dao.GeneralDao;

@Repository
public class GeneralDaoJpa extends BaseDaoJpa<Object, Serializable> implements GeneralDao {

    public GeneralDaoJpa() {
        super(Object.class);
    }
}
