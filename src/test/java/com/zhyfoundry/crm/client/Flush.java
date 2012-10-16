package com.zhyfoundry.crm.client;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.zhyfoundry.crm.TestBase;
import com.zhyfoundry.crm.dao.FatherDao;
import com.zhyfoundry.crm.entity.Father;

public class Flush extends TestBase {

    @Autowired
    @Qualifier("FatherDao")
    protected FatherDao fatherDao;

    @Override
    @Test
    public void execute() {
        try {
            Father f = new Father();
            f.setName("spring");
            fatherDao.save(f);

            assertNotNull(fatherDao.findById(f.getId()));
            System.out.println("以上的操作优先在缓存中进行，很可能还没更新至DB");

            assertNotNull(fatherDao.findByName("spring"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
