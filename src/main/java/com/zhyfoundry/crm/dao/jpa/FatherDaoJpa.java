package com.zhyfoundry.crm.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhyfoundry.crm.core.dao.BaseDaoJpa;
import com.zhyfoundry.crm.dao.FatherDao;
import com.zhyfoundry.crm.entity.Father;

@Repository("FatherDao")
public class FatherDaoJpa extends BaseDaoJpa<Father, Integer> implements FatherDao {

    public FatherDaoJpa() {
        super(Father.class);
    }

    @Override
    public Father findByName(final String name) {
        List<Father> list = findByQuery("from Father where name = ?", name);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
