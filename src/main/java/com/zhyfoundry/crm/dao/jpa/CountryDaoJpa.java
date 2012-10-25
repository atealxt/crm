package com.zhyfoundry.crm.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhyfoundry.crm.core.dao.BaseDaoJpa;
import com.zhyfoundry.crm.dao.CountryDao;
import com.zhyfoundry.crm.entity.Country;

@Repository
public class CountryDaoJpa extends BaseDaoJpa<Country, Integer> implements CountryDao {

    public CountryDaoJpa() {
        super(Country.class);
    }

    @Override
    public Country findByName(final String name) {
        List<Country> list = findByQuery("from Country where name = ?", name);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
