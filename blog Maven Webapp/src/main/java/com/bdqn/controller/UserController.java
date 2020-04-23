package com.bdqn.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bdqn.component.BlogConstants;
import com.bdqn.entity.User;
import com.bdqn.entity.UserWithBLOBs;
import com.bdqn.service.UserService;
import com.bdqn.untity.Result;
import com.google.code.kaptcha.Constants;

@Controller
@RequestMapping("/user")
public class UserController {

	
	 String vpath="client/";
	 @Autowired
	 private UserService userService;
	 
	 //同步注册
	@PostMapping("/register")
	public String register(UserWithBLOBs user,String repassword,String captcha,HttpSession session,Model model){
		
		
		//取出系统验证码
		String sysCaptcha=(String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		Result rs=userService.register( user, repassword,captcha,sysCaptcha);
		if (rs.isSuccess()) {
			//重定向到登录
			return "redirect:/client/regSuccess.html";
		}
		//错误的转发回到注册页面
		model.addAttribute("msg",rs.getContent());
		return vpath+"register";
	}
	
	
	//异步注册json
	@PostMapping("/registerJson")
	@ResponseBody
	public Result registerJson(UserWithBLOBs user,String repassword,HttpSession session,String captcha){
		
		
		//取出系统验证码
		String sysCaptcha=(String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		return userService.register(user, repassword,captcha,sysCaptcha);
	}
	
	
	//异步登录
	@PostMapping("/login")
	@ResponseBody
	public Result<UserWithBLOBs> loginJson(UserWithBLOBs user,String captcha,HttpSession session){
			
		
		//取出系统验证码
		String sysCaptcha=(String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		 Result<UserWithBLOBs> rs=userService.checkLogin(user, captcha, sysCaptcha);
		if (rs.isSuccess()) {			
			session.setAttribute(BlogConstants.LOGIN_SESSION_KEY, rs.getData());
			
		}
		return rs;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute(BlogConstants.LOGIN_SESSION_KEY);
		return "redirect:/";
	}
	
	//修改个人信息
	@RequestMapping("/updateUserInfo")
	@ResponseBody
	public Result updateUserInfo(UserWithBLOBs user,String hobbys[],@RequestParam(name="file",required=false)CommonsMultipartFile file,HttpServletRequest request){
		return userService.updateUserInfo(request, user, hobbys, file);		
	}
	
	
	//更换头像
		@RequestMapping("/changePhoto")
		@ResponseBody
		public Result changePhoto(@RequestParam(name="file",required=false)CommonsMultipartFile file,HttpServletRequest request){
			return userService.updateUserPhoto(request, file);		
		}
		
		//修改密碼		
		@RequestMapping("/resetpass")
		@ResponseBody
		public Result resetpass(String originpass,String pass,String repass,HttpServletRequest request){
			return userService.updatepass(originpass, pass, repass, request);
		}
}
