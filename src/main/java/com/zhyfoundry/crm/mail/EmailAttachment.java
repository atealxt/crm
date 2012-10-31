package com.zhyfoundry.crm.mail;

import static org.apache.commons.lang.StringUtils.EMPTY;

import java.io.IOException;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

public class EmailAttachment {

	@NotBlank
	private String name = EMPTY;
	@NotNull
	private URL url;

	public EmailAttachment(final String name, final URL url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(final URL url) {
		this.url = url;
	}

	public void setUrl(final Resource res) throws IOException {
		url = res.getURL();
	}
}
