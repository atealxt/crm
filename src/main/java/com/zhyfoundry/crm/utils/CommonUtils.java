package com.zhyfoundry.crm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;

public final class CommonUtils {

    private CommonUtils() {}

    public static int String2Int(final String str, final int defaultValue) {
        if (StringUtils.isEmpty(str)) {
            return defaultValue;
        }
        return Integer.parseInt(str);
    }

    public static String md5Hex(final String data) throws NoSuchAlgorithmException  {
    	MessageDigest md = MessageDigest.getInstance("MD5");
        return DigestUtils.md5DigestAsHex(md.digest(data.getBytes()));
    }
}
