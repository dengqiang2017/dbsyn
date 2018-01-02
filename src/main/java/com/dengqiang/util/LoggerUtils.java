package com.dengqiang.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class LoggerUtils {
	public static Logger log = Logger.getLogger(LoggerUtils.class);

	public static void info(Object message) {
		log.info(message);
	}

	public static void error(Object message) {
		log.error("dbsyn>>"+message);
	}

	public static void error(Object message, Throwable t) {
		log.error("dbsyn>>"+message, t);
	}

	/**
	 * 将信息放入到session中
	 * 
	 * @param request
	 * @param msg
	 *            放入的信息
	 */
	public static void msgToSession(HttpServletRequest request, String key,
			Object msg) {
		if (StringUtils.isNotBlank(key) && msg != null && msg != ""&&!"".equals(msg)) {
			request.getSession().setAttribute(key, msg);
		}
	}
}
