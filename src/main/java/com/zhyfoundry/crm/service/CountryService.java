package com.zhyfoundry.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhyfoundry.crm.core.ServiceException;
import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.core.service.PaginationServiceImpl;
import com.zhyfoundry.crm.dao.CountryDao;
import com.zhyfoundry.crm.dao.EnterpriseDao;
import com.zhyfoundry.crm.entity.Country;

@Service
public class CountryService extends PaginationServiceImpl<Country, Integer> {

	@Autowired
	private CountryDao countryDao;
	@Autowired
	private EnterpriseDao enterpriseDao;

	@Override
	protected BaseDao<Country, Integer> getDao() {
		return countryDao;
	}

	public List<Country> getAllCountries() {
		// TODO cache
		return getDao().findByQuery("from Country t order by t.name");
	}

	public List<Country> getCountries(Country condition, final Pager pager) {
		StringBuilder sql = new StringBuilder("from Country t where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (condition.getId() != null) {
			sql.append(" and t.id = ?");
			params.add(condition.getId());
		}
		if (StringUtils.isNotBlank(condition.getName())) {
			sql.append(" and t.name like ?");
			params.add("%" + condition.getName() + "%");
		}
		sql.append(" order by t.name");
		return findByQuery(sql.toString(), pager, params.toArray());
	}

	@Transactional
	public void add(Country country) {
		save(country);
	}

	@Override
	@Transactional
	public void removeById(Integer id) {
		Country country = getDao().findById(id);
		if (enterpriseDao.count("country = ?", country) != 0) {
			logger.warn("该国家已被企业引用，无法删除。id = " + country.getId() + ", name = " + country.getName());
			throw new ServiceException("该国家已被企业引用，无法删除。id = " + country.getId() + ", name = " + country.getName());
		}
		getDao().delete(country);
	}

	@Transactional
	public Country checkAndModify(Country country) {
		Country e = countryDao.findByName(country.getName());
		if (e != null) {
			return e;
		}
		return merge(country);
	}
}
