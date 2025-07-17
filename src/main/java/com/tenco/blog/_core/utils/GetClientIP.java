package com.tenco.blog._core.utils;

import jakarta.servlet.http.HttpServletRequest;

public class GetClientIP {

	public static String getClientIP(HttpServletRequest request) {
		String remoteAddress = "";
		if (request != null) {
			remoteAddress = request.getHeader("X-Forwarded-For");
			if (remoteAddress == null || remoteAddress.isEmpty()) {
				remoteAddress = request.getRemoteAddr();
			}
		}
		return remoteAddress;
	}
}

