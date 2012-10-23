package com.zhyfoundry.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.core.service.BaseServiceImpl;
import com.zhyfoundry.crm.dao.EnterpriseDao;
import com.zhyfoundry.crm.entity.Enterprise;

import edu.emory.mathcs.backport.java.util.Arrays;

@Service
public class EnterpriseService extends BaseServiceImpl<Enterprise, Integer> {

	@Autowired
	private EnterpriseDao EnterpriseDao;

	@Override
	protected BaseDao<Enterprise, Integer> getDao() {
		return EnterpriseDao;
	}

	public List<Enterprise> getEnterprises(Enterprise condition, final Pager pager) {
		StringBuilder sql = new StringBuilder("from Enterprise t where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (condition.getId() != null) {
			sql.append("and t.id = ?");
			params.add(condition.getId());
		}
		if (condition.getKeyword() != null) {
			sql.append("and t.keyword like ?");
			params.add("%" + condition.getKeyword() + "%");
		}
		if (condition.getCountry() != null) {
			sql.append("and t.country.name like ?");
			params.add("%" + condition.getCountry().getName() + "%");
		}
		if (condition.getName() != null) {
			sql.append("and t.name like ?");
			params.add("%" + condition.getName() + "%");
		}
		if (condition.getContact() != null) {
			sql.append("and t.contact like ?");
			params.add("%" + condition.getContact() + "%");
		}
		if (condition.getEmail() != null) {
			sql.append("and t.email like ?");
			params.add("%" + condition.getEmail() + "%");
		}
		if (condition.getTel() != null) {
			sql.append("and t.tel like ?");
			params.add("%" + condition.getTel() + "%");
		}
		if (condition.getMobileNo() != null) {
			sql.append("and t.mobileNo like ?");
			params.add("%" + condition.getMobileNo() + "%");
		}
		if (condition.getFaxNo() != null) {
			sql.append("and t.faxNo like ?");
			params.add("%" + condition.getFaxNo() + "%");
		}
		if (condition.getSource() != null) {
			sql.append("and t.source like ?");
			params.add("%" + condition.getSource() + "%");
		}
		if (condition.getRemark() != null) {
			sql.append("and t.remark like ?");
			params.add("%" + condition.getRemark() + "%");
		}
		final List<Enterprise> Enterprises = EnterpriseDao.findByQuery(sql.toString(), pager, params.toArray());
		for (final Enterprise f : Enterprises) {
			initialize(f.getCountry());
		}
		return Enterprises;
	}
}
