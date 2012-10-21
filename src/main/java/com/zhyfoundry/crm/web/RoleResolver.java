package com.zhyfoundry.crm.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.zhyfoundry.crm.web.controller.AdminController;

public class RoleResolver implements WebArgumentResolver {

	private static Log logger = LogFactory.getLog(RoleResolver.class);

	@Override
	public Object resolveArgument(final MethodParameter methodParameter,
			final NativeWebRequest webRequest) throws Exception {
		if (accept(methodParameter, webRequest)) {
			return UNRESOLVED;
		}
		try {
			final HttpServletResponse resp = (HttpServletResponse) webRequest
					.getNativeResponse();
			if (!resp.isCommitted()) {
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch (final IOException e) {
			logger.error(e.getMessage(), e);
		}
		return UNRESOLVED;
	}

	private boolean accept(MethodParameter methodParameter,
			NativeWebRequest webRequest) {

		final String className = methodParameter.getMethod()
				.getDeclaringClass().getName();
		if ("com.zhyfoundry.crm.web.controller.AdminController"
				.equals(className)) {
			return true;
		}

		// when url start with "/admin", need logged in.
		HttpServletRequest req = (HttpServletRequest) webRequest
				.getNativeRequest();
		if (req.getRequestURI().startsWith(req.getContextPath() + "/admin")) {
			if (!AdminController.OK.equals(req.getSession().getAttribute(
					AdminController.LOGGEDIN))) {
				return false;
			}
		}

		return true;
	}
}