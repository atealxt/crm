package com.zhyfoundry.crm.mail;

import static org.apache.commons.lang.StringUtils.EMPTY;

import java.util.List;

public abstract class EmailBuilder {
	private String subject = EMPTY;

	public final Email buildEmail() {
		final Email email = new Email();
		email.setAttachments(getAttachments());
		email.setContent(getContent());
		email.setSubject(subject);
		email.setTo(getTo());
		return email;
	}

	protected void setSubject(final String subject) {
		this.subject = subject;
	}

	protected abstract String getContent();

	protected abstract String[] getTo();

	protected abstract List<EmailAttachment> getAttachments();
}
