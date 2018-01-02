package com.dengqiang.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 公共工具类
 * @author dengqiang 2017-12-18 14:00:00
 */
public abstract class BaseController {
	
	/**
	 * 从请求头中获取参数的key和value
	 * @param request
	 * @param idname 需要忽略的id名称
	 * @return
	 */
	public Map<String,Object> getKeyAndValue(HttpServletRequest request) {
		Map<String,Object> param=new HashMap<String, Object>();
		Enumeration<String> ens=request.getParameterNames();
		while (ens.hasMoreElements()) {
			String key = ens.nextElement();
			if (StringUtils.isNotBlank(request.getParameter(key))) {
				param.put(key, request.getParameter(key).trim());
			}else{
				param.put(key, request.getParameter(key));
			}
		}
		return param;
	} 

	/** 
	 * 获取绝对路径  已经在返回时添加 '/'
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request, String url) {
		return request.getSession().getServletContext().getRealPath(url)+"/";
	}
	/** 
	 * 获取绝对路径  已经在返回时添加 '/'
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/")+"/";
	}

	/**
	 * 获取服务器域名
	 * @param request
	 * @return 服务器域名
	 */
	public String getServerName(HttpServletRequest request) {
		return "http://" + request.getServerName() +"/";
	} 
	/**
	 * 获取HttpServletRequest请求中的
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		return request;
	} 
}
