package com.mm.dev.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mm.dev.controller.ResponseError;
import com.mm.dev.entity.User;

@Component
public class UserSecurityInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisTemplate<String, Object> redisObjectTemplate;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("preHandle");
		
		
		String token = request.getParameter("access_token");
		String appid = request.getParameter("appid");
		String appsecret = request.getParameter("appsecret");
		User user = (User) redisObjectTemplate.opsForHash().get(token, "user");
		
		if(user == null) {
			ResponseError error = new ResponseError();
			error.setCode(1003);
			error.setMessage("User not login.");
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			String errorJSON = error.toJSONString();
			response.getWriter().print(errorJSON);
			return false;
		} else {
			return true;
		}		
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle");
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion");
		
	}

}
