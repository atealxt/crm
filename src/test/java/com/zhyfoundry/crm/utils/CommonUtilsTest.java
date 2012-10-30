package com.zhyfoundry.crm.utils;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class CommonUtilsTest {

	@Test
	public void testMd5Hex() throws NoSuchAlgorithmException {
		System.out.println(CommonUtils.md5Hex("123456"));
	}
}
