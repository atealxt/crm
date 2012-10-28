package com.zhyfoundry.crm.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhyfoundry.crm.core.dao.BaseDaoJpa;
import com.zhyfoundry.crm.dao.EnterpriseDao;
import com.zhyfoundry.crm.entity.Enterprise;

@Repository
public class EnterpriseDaoJpa extends BaseDaoJpa<Enterprise, Integer> implements EnterpriseDao {

    public EnterpriseDaoJpa() {
        super(Enterprise.class);
    }

    @Override
    public Enterprise findByName(final String name) {
        List<Enterprise> list = findByQuery("from Enterprise where name = ?", name);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
