package com.zhyfoundry.crm.core.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public class BaseDaoJpa<T, PK extends Serializable> extends JpaDaoSupport implements BaseDao<T, PK> {

	private final Class<T> persistentClass;

	public BaseDaoJpa(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Autowired
	public void setSuperEntityManagerFactory(final EntityManagerFactory entityManagerFactory) {
		super.setEntityManagerFactory(entityManagerFactory);
	}

	@Override
	public T findById(final PK id) {
		final Object o = getJpaTemplate().find(persistentClass, id);
		if (o == null) {
			return null;
		}
		final T bean = persistentClass.cast(o);
		return bean;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByQuery(final String query) {
		return getJpaTemplate().find(query);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByQuery(final String hql, final Pager pager) {
		return getJpaTemplate().executeFind(new JpaCallback<T>() {

			@Override
			public T doInJpa(final EntityManager manager) throws PersistenceException {
				Query query = manager.createQuery(hql);
				query.setFirstResult(pager.getStartRow());
				query.setMaxResults(pager.getRecordsPerPage());
				return (T) query.getResultList();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByQuery(final String query, final Object... values) {
		return getJpaTemplate().find(query, values);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByQuery(final String hql, final Pager pager, final Object... values) {
		return getJpaTemplate().executeFind(new JpaCallback<T>() {

			@Override
			public T doInJpa(final EntityManager manager) throws PersistenceException {
				Query query = manager.createQuery(hql);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i + 1, values[i]);
				}
				query.setFirstResult(pager.getStartRow());
				query.setMaxResults(pager.getRecordsPerPage());
				return (T) query.getResultList();
			}
		});
	}

	@Override
	public long count() {
		StringBuilder hql = new StringBuilder("SELECT COUNT(*) FROM ").append(persistentClass.getSimpleName());
		@SuppressWarnings("rawtypes")
		List list = getJpaTemplate().find(hql.toString());
		Long count = (Long) list.get(0);
		return count.longValue();
	}

	@Override
	public long count(final String hql, final Object... values) {
		final String countHql = "SELECT COUNT(*) FROM " + hql.substring(hql.toUpperCase().indexOf("from") + "from".length() + 1);
		return getJpaTemplate().execute(new JpaCallback<Long>() {

			@Override
			public Long doInJpa(final EntityManager manager) throws PersistenceException {
				Query query = manager.createQuery(countHql);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i + 1, values[i]);
				}
				@SuppressWarnings("rawtypes")
				List list = query.getResultList();
				Long count = (Long) list.get(0);
				return count.longValue();
			}
		});
	}

	@Override
	public void execute(final String hql, final Object... values) {
		getJpaTemplate().execute(new JpaCallback<Integer>() {

			@Override
			public Integer doInJpa(final EntityManager manager) throws PersistenceException {
				Query query = manager.createQuery(hql);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i + 1, values[i]);
				}
				return query.executeUpdate();
			}
		});
	}

	@Override
	public T save(final T transientInstance) {
		getJpaTemplate().persist(transientInstance);
		return transientInstance;
	}

	@Override
	public void delete(final T persistentInstance) {
		getJpaTemplate().remove(persistentInstance);
	}

	@Override
	public T merge(final T detachedInstance) {
		return getJpaTemplate().merge(detachedInstance);
	}

	@Override
	public void flush() {
		getJpaTemplate().flush();
	}

	@Override
	public void initialize(final Object obj) {
		Hibernate.initialize(obj);
	}
}
