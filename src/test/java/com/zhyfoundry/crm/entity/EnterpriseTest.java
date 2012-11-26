package com.zhyfoundry.crm.entity;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class EnterpriseTest {

	protected Log logger = LogFactory.getLog(getClass());
	private static final String NAME = "ZHY";
	private static final String CONTACT = "Xiao Ming";

	@Test
	public void test1() {
		Enterprise e = new Enterprise();
		String s = "${contact}";
		Assert.assertEquals("", e.processDSL(s));
		e.setContact(CONTACT);
		Assert.assertEquals(CONTACT, e.processDSL(s));
	}

	@Test
	public void test2() {
		Enterprise e = new Enterprise();
		Assert.assertEquals("", e.processDSL("${contact::}"));
		Assert.assertEquals("Sir or Madam", e.processDSL("${contact::Sir or Madam}"));
	}

	@Test
	public void test3() {
		Enterprise e = new Enterprise();
		String s = "${name}";
		Assert.assertEquals("", e.processDSL(s));
		e.setName(NAME);
		Assert.assertEquals(NAME, e.processDSL(s));
	}

	@Test
	public void test4() {
		Enterprise e = new Enterprise();
		Assert.assertEquals("", e.processDSL("${name::}"));
		Assert.assertEquals("Enterprise", e.processDSL("${name::Enterprise}"));
	}

	@Test
	public void test5() {
		Enterprise e = new Enterprise();
		String s = "${contact|name}";
		e.setContact(CONTACT);
		Assert.assertEquals(CONTACT, e.processDSL(s));
		e.setContact(null);
		e.setName(NAME);
		Assert.assertEquals(NAME, e.processDSL(s));
		e.setName(null);
		Assert.assertEquals("", e.processDSL(s));
	}

	@Test
	public void test6() {
		Enterprise e = new Enterprise();
		String s = "Dear ${contact}, how are you!";
		Assert.assertEquals("Dear , how are you!", e.processDSL(s));
		e.setContact(CONTACT);
		Assert.assertEquals("Dear " + CONTACT + ", how are you!", e.processDSL(s));
	}

	@Test
	public void test7() {
		Enterprise e = new Enterprise();
		Assert.assertEquals("Dear , how are you!", e.processDSL("Dear ${contact::}, how are you!"));
		Assert.assertEquals("Dear Sir or Madam, how are you!", e.processDSL("Dear ${contact::Sir or Madam}, how are you!"));
	}

	@Test
	public void test8() {
		Enterprise e = new Enterprise();
		String s = "Dear ${name}, how are you!";
		Assert.assertEquals("Dear , how are you!", e.processDSL(s));
		e.setName(NAME);
		Assert.assertEquals("Dear " + NAME + ", how are you!", e.processDSL(s));
	}

	@Test
	public void test9() {
		Enterprise e = new Enterprise();
		Assert.assertEquals("Dear , how are you!", e.processDSL("Dear ${name::}, how are you!"));
		Assert.assertEquals("Dear Enterprise, how are you!", e.processDSL("Dear ${name::Enterprise}, how are you!"));
	}

	@Test
	public void test10() {
		Enterprise e = new Enterprise();
		String s = "Dear ${contact|name}, how are you!";
		e.setContact(CONTACT);
		Assert.assertEquals("Dear " + CONTACT + ", how are you!", e.processDSL(s));
		e.setContact(null);
		e.setName(NAME);
		Assert.assertEquals("Dear " + NAME + ", how are you!", e.processDSL(s));
		e.setName(null);
		Assert.assertEquals("Dear , how are you!", e.processDSL(s));
	}
}
