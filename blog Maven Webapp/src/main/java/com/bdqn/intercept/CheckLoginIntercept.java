package com.bdqn.intercept;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bdqn.component.BlogConstants;
import com.bdqn.untity.ResponseUtil;
import com.bdqn.untity.Result;

/**
 * 登录验证拦截器
 * 
 */
public class  CheckLoginIntercept extends HandlerInterceptorAdapter{

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
		
		Object object=request.getSession().getAttribute(BlogConstants.LOGIN_SESSION_KEY);
		if (object==null) {
			if (flag) {
				ResponseUtil.sendJson(response, Result.createError("非法请求"));
			}else {
				ResponseUtil.alertRedirect(response, "登录已失效", "/client/login.html");
			}
			
			return false;
		}
		
		return true;
	}

	
	
}
