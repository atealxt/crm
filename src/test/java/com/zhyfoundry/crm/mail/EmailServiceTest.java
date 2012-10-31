package com.zhyfoundry.crm.mail;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhyfoundry.crm.TestBase;

public class EmailServiceTest extends TestBase {

	private Email email;

	@Autowired
	private EmailService emailService;

	@Override
	@Before
	public void setUp() {
		email = new Email();
		email.setFrom("a@aaaaaaaa.com");
		email.setTo("b@aaaaaaaa.com", "c@aaaaaaaa.com");
		email.setSubject("test email subject");
		email.setContent("test email content text a<b>b</b>c<i>d</i>e ");
		email.setHtml(true);

		final URL baseUrl = getClass().getResource("/");
		try {
			email.getAttachments().add(new EmailAttachment("test file 1", new URL(baseUrl, "a.txt")));
			email.getAttachments().add(new EmailAttachment("test file 2", new URL(baseUrl, "b.txt")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() throws Exception {
		emailService.send(email);
	}
}
