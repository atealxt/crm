package com.zhyfoundry.crm.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhyfoundry.crm.core.dao.BaseDaoJpa;
import com.zhyfoundry.crm.dao.ChildDao;
import com.zhyfoundry.crm.entity.Child;

@Repository("ChildDao")
public class ChildDaoJpa extends BaseDaoJpa<Child, Integer> implements ChildDao {

    public ChildDaoJpa() {
        super(Child.class);
    }

    @Override
    public Child findByName(final String name) {
        List<Child> list = findByQuery("from Child where name = ?", name);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
