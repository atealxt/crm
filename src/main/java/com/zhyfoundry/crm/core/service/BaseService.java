package com.zhyfoundry.crm.core.service;

import java.io.Serializable;

public interface BaseService<T, PK extends Serializable> {

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#findById(Serializable) */
    T get(PK id);

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#save(Object) */
    T save(T object);

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#delete(Object) */
    void remove(T object);

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#initialize(Object) */
    void initialize(final Object obj);

    /** @see com.zhyfoundry.crm.core.dao.BaseDao#count() */
    long count();
}
