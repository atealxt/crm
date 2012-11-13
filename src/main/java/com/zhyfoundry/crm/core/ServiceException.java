package com.zhyfoundry.crm.core;

import java.util.ArrayList;
import java.util.List;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3805220635264340400L;

	private final List<Object> reasons = new ArrayList<Object>();

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException addReason(Object reason) {
		reasons.add(reason);
		return this;
	}

	public List<Object> getReasons() {
		return reasons;
	}
}
