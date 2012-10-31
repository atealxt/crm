package com.zhyfoundry.crm.mail;

import static org.apache.commons.lang.ArrayUtils.EMPTY_STRING_ARRAY;
import static org.apache.commons.lang.StringUtils.EMPTY;

import java.util.ArrayList;
import java.util.List;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

public class Email {

	@NotBlank
	private String from = EMPTY;
	@NotEmpty
	private String[] to = EMPTY_STRING_ARRAY;
	@NotBlank
	private String subject = EMPTY;
	@NotNull
	private String content = EMPTY;
	private List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(final String... to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public List<EmailAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(final List<EmailAttachment> attachments) {
		this.attachments = attachments;
	}
}
