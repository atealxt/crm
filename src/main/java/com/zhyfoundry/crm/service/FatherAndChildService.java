package com.zhyfoundry.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhyfoundry.crm.core.DSContextHolder;
import com.zhyfoundry.crm.core.DataSourceMap;
import com.zhyfoundry.crm.dao.ChildDao;
import com.zhyfoundry.crm.dao.FatherDao;
import com.zhyfoundry.crm.entity.Child;
import com.zhyfoundry.crm.entity.Father;

@Service("FatherAndChildService")
public class FatherAndChildService {

    @Autowired
    @Qualifier("ChildDao")
    protected ChildDao childDao;
    @Autowired
    @Qualifier("FatherDao")
    protected FatherDao fatherDao;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void testTransaction() {
        final Child cc = new Child();
        cc.setName("ff");
        cc.setFather(fatherDao.findByName("tom"));
        childDao.save(cc);

        childDao.flush();
        System.out.println("insert child success");

        testTransactionERROR();
    }

    private void testTransactionERROR() {
        System.out.println("will auto rollback");
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void testTransactionMultiDS_DS1() {

        final Father f = new Father();
        f.setName("spring");
        fatherDao.save(f);
        fatherDao.flush();

        // 不能在一个事务里往2个DS里更新
        // DSContextHolder.setDSContext(DataSourceMap.ORACLE);
        // fatherDao.insert(f);
        // fatherDao.getHibernateTemplate().flush();

        testTransactionERROR();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void testTransactionMultiDS_DS2() {
        final Father f = new Father();
        f.setName("spring");
        DSContextHolder.setDSContext(DataSourceMap.ORACLE);
        fatherDao.save(f);
        fatherDao.flush();

        testTransactionERROR();
    }

}
