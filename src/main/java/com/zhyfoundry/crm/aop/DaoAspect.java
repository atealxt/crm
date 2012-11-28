package com.zhyfoundry.crm.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DaoAspect {

    private static Log logger = LogFactory.getLog(DaoAspect.class);

//    @Around("execution(* com.zhyfoundry.crm.core.dao.*.find*(..))")
    public Object pointcutFind(final ProceedingJoinPoint point) throws Throwable {
        logger.debug(point.toLongString());
        return point.proceed(point.getArgs());
    }

//    @Around("execution(* com.zhyfoundry.crm.dao.*.find*(..))")
    @Around("within(com.zhyfoundry.crm.dao.FatherDao+) && execution(* findByName(java.lang.String))")
    public Object pointcutFind2(final ProceedingJoinPoint point) throws Throwable {
        logger.debug(point.toLongString());
        return point.proceed(point.getArgs());
    }
}
