package com.zhyfoundry.crm.service;

import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhyfoundry.crm.dao.FatherDao;
import com.zhyfoundry.crm.entity.Father;

@Service("FlushService")
public class FlushService {

    @Autowired
    @Qualifier("FatherDao")
    protected FatherDao fatherDao;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void execute(final Father f) {

        fatherDao.save(f);
        // 不flush的话，缓存有，db不一定马上有。尽可能不要写这句话。
        // fatherDao.getHibernateTemplate().flush();
        assertNotNull(fatherDao.findById(f.getId()));
        System.out.println("以上的操作优先在缓存中进行，很可能还没更新至DB");

        // 估计是直接检索DB时的处理，控制程序先触发flush了。所以先Commit后再检索，DB里就有了。
        // 自动flush在这里可以表现出工作的很好:P
        assertNotNull(fatherDao.findByName("spring"));
    }
}
