package com.zhyfoundry.crm.core.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.zhyfoundry.crm.core.dao.Pager;

public abstract class PaginationServiceImpl<T, PK extends Serializable> extends BaseServiceImpl<T, PK> implements PaginationService<T, PK> {

	@Override
	public List<T> findByQuery(String query, Pager pager, Object... values) {
		return findByQuery(query, query, pager, values);
	}

	@Override
	public List<T> findByQuery(final String countQuery, String query, Pager pager, Object... values) {
		pager.setTotalRows(getDao().count(countQuery, values));
		if (pager.getTotalRows() == 0) {
			return Collections.emptyList();
		}
		// TODO if the page is too large to get items, decrease the page before fetch items.
		return getDao().findByQuery(query, pager, values);
	}
}
