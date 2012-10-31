package com.zhyfoundry.crm.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService  {

	@Autowired
	private JavaMailSender emailSender;

	public void send(final Email email) {
		if (email == null) {
			throw new IllegalArgumentException();
		}
		final MimeMessage mimeMessage = createMimeMessage(email);
		emailSender.send(mimeMessage);
	}

	private MimeMessage createMimeMessage(final Email email) {
		final boolean isMultipart = !email.getAttachments().isEmpty();
		final MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(emailSender.createMimeMessage(), isMultipart);

			helper.setFrom(email.getFrom());
			helper.setTo(email.getTo());
			helper.setSubject(email.getSubject());
			helper.setText(email.getContent(), false);

			for (final EmailAttachment each : email.getAttachments()) {
				helper.addAttachment(each.getName(), new UrlResource(each.getUrl()));
			}
		} catch (final MessagingException e) {
			throw new MailParseException(e.getCause());
		}
		return helper.getMimeMessage();
	}
}
