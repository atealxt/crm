package com.zhyfoundry.crm.core.dao;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * The interface should exist without implement any ORM framework
 * </p>
 * <p>
 * 此interface应脱离于ORM框架技术而存在
 * </p>
 */
public interface BaseDao<T, PK extends Serializable> {

	T findById(PK id);

	List<T> findByQuery(String query);

	/**
	 * @param pager
	 *            contain firstResult and maxResults
	 */
	List<T> findByQuery(final String query, final Pager pager);

	/**
	 * @param values
	 *            query's values
	 */
	List<T> findByQuery(String query, Object... values);

	/**
	 * @param pager
	 *            contain firstResult and maxResults
	 * @param values
	 *            query's values
	 */
	List<T> findByQuery(final String query, final Pager pager, final Object... values);

	long count();

	long count(final String query, final Object... values);

	T save(T transientInstance);

	void delete(T persistentInstance);

	T merge(T detachedInstance);

	void flush();

	/**
	 * <p>
	 * Initiative loading<br>
	 * Example: xxDao.initialize(Parent.getChildren())
	 * </p>
	 * <p>
	 * 主动加载<br>
	 * 例如: xxDao.initialize(Parent.getChildren())
	 * </p>
	 *
	 * @param obj
	 *            Loading object
	 */
	void initialize(Object obj);
}
