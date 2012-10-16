package com.zhyfoundry.crm.service;

import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhyfoundry.crm.core.DSContextHolder;
import com.zhyfoundry.crm.core.DataSourceMap;
import com.zhyfoundry.crm.dao.FatherDao;
import com.zhyfoundry.crm.entity.Father;

/** 框架改为JPA后没对此进行过测试，实际运用的时候再测吧，jpa应该支持的比hib要更好 */
@Service("MultiDataSourceService")
public class MultiDataSourceService {

    @Autowired
    @Qualifier("FatherDao")
    protected FatherDao fatherDao;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void execute(final Father f) {
        fatherDao.save(f);
        fatherDao.flush();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void executeOracle(final Father f) {
        DSContextHolder.setDSContext(DataSourceMap.ORACLE);
        fatherDao.save(f);
        fatherDao.flush();
        DSContextHolder.clearDSContext();
    }

    public void multiDSSearch(final Integer id) {
        try {
            assertNotNull(fatherDao.findById(id));// ds 1
            DSContextHolder.setDSContext(DataSourceMap.ORACLE);
            assertNotNull(fatherDao.findById(id));// ds 2
            DSContextHolder.clearDSContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
