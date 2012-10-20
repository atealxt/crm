package com.zhyfoundry.crm.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class RoleResolver implements WebArgumentResolver {

	private static Log logger = LogFactory.getLog(RoleResolver.class);

	@Override
	public Object resolveArgument(final MethodParameter methodParameter,
			final NativeWebRequest webRequest) throws Exception {
		if (accept(webRequest)) {
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

	private boolean accept(NativeWebRequest webRequest) {
		// TODO when url start with "/admin/", need logged in.
		return true;
	}
}