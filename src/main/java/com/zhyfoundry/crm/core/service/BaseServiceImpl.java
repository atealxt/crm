package com.zhyfoundry.crm.core.service;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhyfoundry.crm.core.dao.BaseDao;

public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

    protected final Log logger = LogFactory.getLog(getClass());

    abstract protected BaseDao<T, PK> getDao();

    @Override
    public T get(final PK id) {
        return getDao().findById(id);
    }

    @Override
    public T save(final T object) {
        return getDao().save(object);
    }

    @Override
    public void remove(final T object) {
        getDao().delete(object);
    }

    @Override
    public void initialize(final Object obj) {
        getDao().initialize(obj);
    }

    @Override
    public long count() {
        return getDao().count();
    }
}
