package com.zhyfoundry.crm.core.service;

import java.io.Serializable;
import java.util.List;

import com.zhyfoundry.crm.core.dao.Pager;

public abstract class PaginationServiceImpl<T, PK extends Serializable> extends BaseServiceImpl<T, PK> implements PaginationService<T, PK> {

	@Override
	public List<T> findByQuery(String query, Pager pager, Object... values) {
		pager.setTotalRows(getDao().count(query, values));
		return getDao().findByQuery(query, pager, values);
	}
}
