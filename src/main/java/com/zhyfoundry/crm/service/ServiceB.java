package com.zhyfoundry.crm.service;

import org.springframework.stereotype.Service;

@Service("ServiceB")
public class ServiceB {

    public void testTransaction() {
        System.out.println("will auto rollback");
        throw new UnsupportedOperationException();
    }
}
