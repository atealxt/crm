package com.zhyfoundry.crm.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseController {

	protected Log logger = LogFactory.getLog(getClass());
	private static final String ERRORS = "ERRORS";
	private static final String INFOS = "INFOS";

	@SuppressWarnings("unchecked")
	public static List<Exception> getErrors(final HttpServletRequest req) {
		Object o = req.getAttribute(ERRORS);
		if (o == null) {
			return Collections.emptyList();
		}
		return (List<Exception>) o;
	}

	public void appendError(final HttpServletRequest req, Exception e) {
		@SuppressWarnings("unchecked")
		List<Exception> errors = (List<Exception>) req.getAttribute(ERRORS);
		if (errors == null) {
			errors = new ArrayList<Exception>();
			req.setAttribute(ERRORS, errors);
		}
		errors.add(e);
	}

	@SuppressWarnings("unchecked")
	public List<PageMessage> getInfos(final HttpServletRequest req) {
		return (List<PageMessage>) req.getAttribute(INFOS);
	}

	public void appendInfo(final HttpServletRequest req, String key) {
		List<PageMessage> list = getMessages(req);
		list.add(new PageMessage(key));
	}

	public void appendInfo(final HttpServletRequest req, String key, Object... arg) {
		List<PageMessage> list = getMessages(req);
		list.add(new PageMessage(key).addArg(arg));
	}

	private List<PageMessage> getMessages(final HttpServletRequest req) {
		@SuppressWarnings("unchecked")
		List<PageMessage> list = (List<PageMessage>) req.getAttribute(INFOS);
		if (list == null) {
			list = new ArrayList<PageMessage>();
			req.setAttribute(INFOS, list);
		}
		return list;
	}
}
