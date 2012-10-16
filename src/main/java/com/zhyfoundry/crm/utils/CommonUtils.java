package com.zhyfoundry.crm.utils;

import org.apache.commons.lang.StringUtils;

public final class CommonUtils {

    private CommonUtils() {}

    public static int String2Int(final String str, final int defaultValue) {
        if (StringUtils.isEmpty(str)) {
            return defaultValue;
        }
        return Integer.parseInt(str);
    }
}
