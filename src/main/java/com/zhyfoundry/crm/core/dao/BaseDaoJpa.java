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

    private Class<T> persistentClass;

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
                query.setFirstResult(pager.getFirstResult());
                query.setMaxResults(pager.getMaxResults());
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
                    query.setParameter(i, values[i]);
                }
                query.setFirstResult(pager.getFirstResult());
                query.setMaxResults(pager.getMaxResults());
                return (T) query.getResultList();
            }
        });
    }

    @Override
    public long count() {
        StringBuilder hql = new StringBuilder("SELECT COUNT(*) FROM ").append(persistentClass.getSimpleName());
        @SuppressWarnings("rawtypes") List list = getJpaTemplate().find(hql.toString());
        Long count = (Long) list.get(0);
        return count.longValue();
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
