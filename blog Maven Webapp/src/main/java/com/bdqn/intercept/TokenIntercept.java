package com.bdqn.intercept;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bdqn.untity.ResponseUtil;
import com.bdqn.untity.Result;

public class TokenIntercept extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String accept=request.getHeader("Accept");
		boolean flag=false;
		if (StringUtils.isNotBlank(accept)) {
			if (accept.startsWith("application/json")||accept.equals("*/*")) {
				flag=true;
			}
		}
		
		
		String token=request.getParameter("token");
		String sessionToken=(String) request.getSession().getAttribute("token");
		if (StringUtils.isBlank(token)||StringUtils.isBlank(sessionToken)||!sessionToken.equals(token)) {
			if (flag) {
				ResponseUtil.sendJson(response, Result.createError("非法请求"));
			}else {
				ResponseUtil.alertRedirect(response, "非法操作", "/");
			}
			
			
			
			return false;
		}
		
		return true;
	}

	
	
}
