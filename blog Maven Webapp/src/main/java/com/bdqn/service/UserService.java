package com.bdqn.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bdqn.entity.User;
import com.bdqn.entity.UserWithBLOBs;
import com.bdqn.untity.Result;

/**
 * 用户业务
 * @author RP
 *
 */
public interface UserService {

	//注册
	public Result register(UserWithBLOBs user,String repassword,String captcha,String sysCaptcha);
	
	//登录 
	public Result<UserWithBLOBs> checkLogin(UserWithBLOBs user,String captcha,String sysCaptcha);
	
	//修改用户信息
	public Result updateUserInfo(HttpServletRequest request,UserWithBLOBs user,String hobbys[],CommonsMultipartFile file);
	
	
	
	//修改头像
	public Result updateUserPhoto(HttpServletRequest request,CommonsMultipartFile file);
	
	
	/**
	 * 重置密码
	 * @param originpass
	 * @param pass
	 * @param repass
	 * @return
	 */
	public Result updatepass(String originpass,String pass,String repass,HttpServletRequest request);
}
