package com.zhyfoundry.crm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static int String2Int(final String str, final int defaultValue) {
        if (StringUtils.isEmpty(str)) {
            return defaultValue;
        }
        if (StringUtils.isNumeric(str)) {
            return Integer.parseInt(str);
        }
        return defaultValue;
    }

    public static String md5Hex(final String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return DigestUtils.md5DigestAsHex(md.digest(data.getBytes()));
    }

    public static String brief(String source, int length) {
        if (source == null) {
            return source;
        }
        if (StringUtils.isEmpty(source)) {
            return source;
        }
        if (source.length() < length) {
            return source;
        }
        return source.substring(0, length) + "...";
    }
}
