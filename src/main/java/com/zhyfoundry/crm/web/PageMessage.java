package com.zhyfoundry.crm.web;

import java.util.ArrayList;
import java.util.List;

public class PageMessage {

	private String key;
	private String defaultMessage;
	private List<Object> args = new ArrayList<Object>();

	public PageMessage() {
		super();
	}

	public PageMessage(String key) {
		super();
		this.key = key;
	}

	public PageMessage(String key, List<Object> args) {
		super();
		this.key = key;
		this.args = args;
	}

	public PageMessage(String key, String defaultMessage) {
		super();
		this.key = key;
		this.defaultMessage = defaultMessage;
	}

	public PageMessage(String key, String defaultMessage, List<Object> args) {
		super();
		this.key = key;
		this.defaultMessage = defaultMessage;
		this.args = args;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public List<Object> getArgs() {
		return args;
	}

	public void setArgs(List<Object> args) {
		this.args = args;
	}

	public PageMessage addArg(Object... arg) {
		for (Object e : arg) {
			args.add(e);
		}
		return this;
	}
}
