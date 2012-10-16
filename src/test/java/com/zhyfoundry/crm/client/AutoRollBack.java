package com.zhyfoundry.crm.client;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.zhyfoundry.crm.TestBase;
import com.zhyfoundry.crm.dao.ChildDao;
import com.zhyfoundry.crm.dao.FatherDao;
import com.zhyfoundry.crm.service.FatherAndChildService;
import com.zhyfoundry.crm.service.ServiceA;

public class AutoRollBack extends TestBase {

    @Autowired
    @Qualifier("FatherAndChildService")
    protected FatherAndChildService fatherAndChildService;
    @Autowired
    @Qualifier("ServiceA")
    protected ServiceA serviceA;
    @Autowired
    @Qualifier("ChildDao")
    protected ChildDao childDao;
    @Autowired
    @Qualifier("FatherDao")
    protected FatherDao fatherDao;

    @Test
    public void testAutoRollback1() {
        try {
            fatherAndChildService.testTransaction();
            assertNull(childDao.findByName("ff"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAutoRollback2() {
        try {
            serviceA.testTransaction();
            assertNull(childDao.findByName("ff"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() throws Exception {}

}
