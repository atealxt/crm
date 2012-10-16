package com.zhyfoundry.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhyfoundry.crm.dao.ChildDao;
import com.zhyfoundry.crm.dao.FatherDao;
import com.zhyfoundry.crm.entity.Child;

@Service("ServiceA")
public class ServiceA {

    @Autowired
    @Qualifier("ChildDao")
    protected ChildDao childDao;
    @Autowired
    @Qualifier("FatherDao")
    protected FatherDao fatherDao;
    @Autowired
    // @Qualifier("ServiceBImpl")//使用spring 1.x的配置风格时，要用的注入依赖名称
    @Qualifier("ServiceB")
    private ServiceB serviceB;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void testTransaction() {
        Child cc = new Child();
        cc.setName("ff");
        cc.setFather(fatherDao.findByName("tom"));
        childDao.save(cc);

        childDao.flush();
        System.out.println("insert child success");

        serviceB.testTransaction();
    }

}
