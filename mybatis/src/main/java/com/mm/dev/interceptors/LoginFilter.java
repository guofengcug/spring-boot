package com.mm.dev.interceptors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * before HandlerInterceptor
 * @author Created by GaryGuo on 4/11/2015
 *
 */

public class LoginFilter implements Filter {

	private String excludePatterns = "";
	


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("doFilter");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 获取用户IP地址
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for"); // request取ip
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取用户域名地址
	 */
	private String getDomain(HttpServletRequest request) {
		return request.getHeader("Host");
	}
	
	/**
	 * 获取用户域名地址
	 */
	private String getSessionID(HttpServletRequest request) {
		String cookie = request.getHeader("Cookie");
		return cookie.substring(cookie.indexOf("=") + 1);
	}
	/**
	 * 判断是否为Ajax请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return request.getRequestURI().endsWith(".json");
	}

	public static boolean isFilter(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (uri.endsWith(".jsp")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为过滤
	 */
	public boolean matchExcludePatterns(String reqUrl) {
		String[] array = getExcludePatterns().split(",");
		boolean flag = false;
		for (String string : array) {
			if (!"/".endsWith(reqUrl)) {
				if (reqUrl.contains(string)) {
					flag = true;
					break;
				}
			} else {
				flag = true;
				break;
			}
		}
		return flag;
	}



	@Override
	public void init(FilterConfig cfg) throws ServletException {
		this.excludePatterns = cfg.getInitParameter("excludePatterns");
		if (excludePatterns == null) {
			excludePatterns = "/";
		}
	}

	public String getExcludePatterns() {
		return excludePatterns;
	}
}
