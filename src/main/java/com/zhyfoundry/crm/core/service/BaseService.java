package com.zhyfoundry.crm.core.service;

import java.io.Serializable;

public interface BaseService<T, PK extends Serializable> {

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#findById(Serializable) */
    T get(PK id);

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#save(Object) */
    T save(T object);

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#merge(Object) */
    T merge(T object);

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#delete(Object) */
    void removeById(PK id);

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#initialize(Object) */
    void initialize(final Object obj);

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#count() */
    long count();

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#execute(String, Object...) */
    void execute(final String query, final Object... values);
}
