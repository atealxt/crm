package com.zhyfoundry.crm.core;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/** 为那些没有配置在spring下，但却想使用配置在spring下的类而设计的工具类 */
public final class DIManager {

    private static Log logger = LogFactory.getLog(DIManager.class);
    private static WebApplicationContext ctx;// for web
    private static AbstractApplicationContext applicationContext;// for client

    private DIManager() {}

    static {
        ServletContext sc = contextBySpring();
        if (sc != null) {
            logger.info("DIManager WebApplicationContext init");
            ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        } else if (ctx == null) {
            logger.info("DIManager ClassPathXmlApplicationContext init");
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            applicationContext.registerShutdownHook();
        }
    }

    private static ServletContext contextBySpring() {
        WebApplicationContext wctx = ContextLoader.getCurrentWebApplicationContext();
        if (wctx != null) {
            return wctx.getServletContext();
        }
        return null;
    }

    public static <T> T getBean(final Class<T> clazz) {
        try {
            if (ctx != null) {
                return ctx.getBean(clazz);
            } else if (applicationContext != null) {
                return applicationContext.getBean(clazz);
            } else {
                logger.error("DIManager setting up error!");
                return null;
            }
        } catch (RuntimeException re) {
            logger.error(re);
            throw re;
        }
    }

    public static <T> T getBean(final String name, final Class<T> clazz) {
        try {
            if (ctx != null) {
                return ctx.getBean(name, clazz);
            } else if (applicationContext != null) {
                return applicationContext.getBean(name, clazz);
            } else {
                logger.error("DIManager setting up error!");
                return null;
            }
        } catch (RuntimeException re) {
            logger.error(re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(final String name) {
        return (T) get(name);
    }

    private static Object get(final String name) {
        try {
            if (ctx != null) {
                return ctx.getBean(name);
            } else if (applicationContext != null) {
                return applicationContext.getBean(name);
            } else {
                logger.error("DIManager setting up error!");
                return null;
            }
        } catch (RuntimeException re) {
            logger.error(re);
            throw re;
        }
    }

    /** 也可以用注解来代替本函数，但感觉比较麻烦，还需要自定义Qualifier接口(spring 3.0 reference part 3.9) */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(final String name, final Object... parameter) {
        return (T) get(name, parameter);
    }

    private static Object get(final String name, final Object... parameter) {
        try {
            if (ctx != null) {
                return ctx.getBean(name);
            } else if (applicationContext != null) {
                return applicationContext.getBean(name, parameter);
            } else {
                logger.error("DIManager setting up error!");
                return null;
            }
        } catch (RuntimeException re) {
            logger.error(re);
            throw re;
        }
    }
}
