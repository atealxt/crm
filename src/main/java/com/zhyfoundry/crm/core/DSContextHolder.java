package com.zhyfoundry.crm.core;

/**
 * 多数据源支持类<br>
 * 换DS前：DSContextHolder.setDSContext();<br>
 * 执行完后需要手动清空：DSContextHolder.clearDSContext();
 * 
 * <p>注意在OSIV下不能使用！</p>
 */
public class DSContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDSContext(String key) {
        contextHolder.set(key);
    }

    public static String getDSContext() {
        return contextHolder.get();
    }

    public static void clearDSContext() {
        contextHolder.remove();
    }
}
