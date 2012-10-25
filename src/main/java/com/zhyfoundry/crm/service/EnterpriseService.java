package com.zhyfoundry.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.core.service.PaginationServiceImpl;
import com.zhyfoundry.crm.dao.CountryDao;
import com.zhyfoundry.crm.dao.EnterpriseDao;
import com.zhyfoundry.crm.entity.Country;
import com.zhyfoundry.crm.entity.Enterprise;

@Service
public class EnterpriseService extends PaginationServiceImpl<Enterprise, Integer> {

	@Autowired
	private EnterpriseDao enterpriseDao;
	@Autowired
	private CountryDao countryDao;

	@Override
	protected BaseDao<Enterprise, Integer> getDao() {
		return enterpriseDao;
	}

	public List<Enterprise> getEnterprises(Enterprise condition, final Pager pager) {
		StringBuilder sql = new StringBuilder("from Enterprise t where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (condition.getId() != null) {
			sql.append(" and t.id = ?");
			params.add(condition.getId());
		}
		if (StringUtils.isNotBlank(condition.getKeyword())) {
			sql.append(" and t.keyword like ?");
			params.add("%" + condition.getKeyword() + "%");
		}
		if (condition.getCountry() != null && StringUtils.isNotBlank(condition.getCountry().getName())) {
			sql.append(" and t.country.name like ?");
			params.add("%" + condition.getCountry().getName() + "%");
		}
		if (StringUtils.isNotBlank(condition.getName())) {
			sql.append(" and t.name like ?");
			params.add("%" + condition.getName() + "%");
		}
		if (StringUtils.isNotBlank(condition.getContact())) {
			sql.append(" and t.contact like ?");
			params.add("%" + condition.getContact() + "%");
		}
		if (StringUtils.isNotBlank(condition.getEmail())) {
			sql.append(" and t.email like ?");
			params.add("%" + condition.getEmail() + "%");
		}
		if (StringUtils.isNotBlank(condition.getTel())) {
			sql.append(" and t.tel like ?");
			params.add("%" + condition.getTel() + "%");
		}
		if (StringUtils.isNotBlank(condition.getMobileNo())) {
			sql.append(" and t.mobileNo like ?");
			params.add("%" + condition.getMobileNo() + "%");
		}
		if (StringUtils.isNotBlank(condition.getFaxNo())) {
			sql.append(" and t.faxNo like ?");
			params.add("%" + condition.getFaxNo() + "%");
		}
		if (StringUtils.isNotBlank(condition.getSource())) {
			sql.append(" and t.source like ?");
			params.add("%" + condition.getSource() + "%");
		}
		if (StringUtils.isNotBlank(condition.getRemark())) {
			sql.append(" and t.remark like ?");
			params.add("%" + condition.getRemark() + "%");
		}
		final List<Enterprise> Enterprises = findByQuery(sql.toString(), pager, params.toArray());
		for (final Enterprise f : Enterprises) {
			initialize(f.getCountry());
		}
		return Enterprises;
	}

	@Transactional
	public void add(Enterprise enterprise) {
		enterprise.setCountry(initCountry(enterprise));
		add(enterprise);
	}

	@Transactional
	public void modify(Enterprise enterprise) {
		enterprise.setCountry(initCountry(enterprise));
		merge(enterprise);
	}

	private Country initCountry(Enterprise enterprise) {
		Country country = countryDao.findByName(enterprise.getCountry().getName());
		if (country == null) {
			country = new Country(enterprise.getCountry().getName());
			countryDao.save(country);
		}
		return country;
	}
}
