package com.zhyfoundry.crm.client;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.zhyfoundry.crm.TestBase;
import com.zhyfoundry.crm.core.DSContextHolder;
import com.zhyfoundry.crm.core.DataSourceMap;
import com.zhyfoundry.crm.dao.FatherDao;
import com.zhyfoundry.crm.entity.Father;
import com.zhyfoundry.crm.service.FatherAndChildService;

public class MultiDataSource extends TestBase {

    @Autowired
    @Qualifier("FatherDao")
    protected FatherDao fatherDao;

    @Autowired
    @Qualifier("FatherAndChildService")
    protected FatherAndChildService fatherAndChildService;

    @Test
    public void hello() {
        try {
            Father f = new Father();
            f.setName("spring");
            fatherDao.save(f);

            fatherDao.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void anotherThread() {
        try {
            Father f = new Father();
            f.setName("spring");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dSContextHolder() {
        try {
            Father f = new Father();
            f.setName("spring");

            DSContextHolder.setDSContext(DataSourceMap.ORACLE);
            fatherDao.save(f);
            fatherDao.flush();
            DSContextHolder.clearDSContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void autoRollBack() {
        System.out.println("test TransactionMultiDS start");
        try {
            fatherAndChildService.testTransactionMultiDS_DS1();
        } catch (Exception e) {
        }
        try {
            fatherAndChildService.testTransactionMultiDS_DS2();
        } catch (Exception e) {
        }
    }

    @Override
    public void execute() throws Exception {}

}
