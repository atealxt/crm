package com.zhyfoundry.crm.core.service;

import java.io.Serializable;
import java.util.List;

import com.zhyfoundry.crm.core.dao.Pager;

public interface PaginationService<T, PK extends Serializable> extends BaseService<T, PK> {

	List<T> findByQuery(final String query, final Pager pager, final Object... values);
}
