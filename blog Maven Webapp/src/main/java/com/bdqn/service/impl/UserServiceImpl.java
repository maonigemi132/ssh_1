package com.bdqn.service.impl;

import java.io.File;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bdqn.component.BlogConstants;
import com.bdqn.dao.UserMapper;
import com.bdqn.entity.User;
import com.bdqn.entity.UserWithBLOBs;
import com.bdqn.service.UserService;
import com.bdqn.untity.FileResult;
import com.bdqn.untity.FileUtil;
import com.bdqn.untity.MD5;
import com.bdqn.untity.Result;
import com.bdqn.untity.TokenUtil;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Result register(UserWithBLOBs user, String repassword,String captcha,String sysCaptcha) {

		
		if (StringUtils.isBlank(captcha)||StringUtils.isBlank(sysCaptcha)||!sysCaptcha.equals(captcha)) {
			return Result.createError("验证码错误");
		}
		if (user==null||StringUtils.isBlank(repassword)||StringUtils.isBlank(user.getEmail())||
				StringUtils.isBlank(user.getPassword())||!user.getPassword().equals(repassword)) {
			return Result.createError("参数错误");
		}
		
		//解密
		String str=new String(Base64.getDecoder().decode(user.getPassword()));
		if (!str.contains("-")) {
			return Result.createError("非法操作");	
		}
		
		String strs[]=str.split("-");
		if (!TokenUtil.encodeStr.equals(strs[1])) {
			return Result.createError("非法操作");
		}
		//数据库中不可以保存明码
		user.setPassword(MD5.md5(strs[0].getBytes()));
		
		//验证邮箱是否存在
		if (userMapper.selectCountUserEmail(user.getEmail())>0) {
			return Result.createError("此邮箱已被注册");
		}
		user.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
		user.setCreatetime(new Date());
		user.setStatus(0);
		user.setPhotourl("header2.png");
		if (userMapper.insert(user)>0) {
			return Result.createSuccess("注册成功");
		}
		return Result.createError("注册失败");
	}

	@Override
	public Result<UserWithBLOBs> checkLogin(UserWithBLOBs user, String captcha, String sysCaptcha) {
		if (StringUtils.isBlank(captcha)||StringUtils.isBlank(sysCaptcha)||!sysCaptcha.equals(captcha)) {
			return Result.createError("验证码错误");
		}
		if (user==null||StringUtils.isBlank(user.getEmail())||
				StringUtils.isBlank(user.getPassword())) {
			return Result.createError("参数错误");
		}
		
		//解密
		String str=new String(Base64.getDecoder().decode(user.getPassword()));
		if (!str.contains("-")) {
			return Result.createError("非法操作");	
		}		
		
		String strs[]=str.split("-");
		if (!TokenUtil.encodeStr.equals(strs[1])) {
			return Result.createError("非法操作");
		}
		//数据库中不可以保存明码		
		user.setPassword(MD5.md5(strs[0].getBytes()));
		user=userMapper.selectUserByEmialPass(user);
		
		if (user!=null) {
			if (user.getStatus()!=0) {
				return Result.createError("此账户已被禁用");
			}
			user.setPassword(null);
			return Result.createSuccess(user,"登录成功");
		}
		
		
		return Result.createError("账户名或密码错误");
	}

	@Override
	public Result updateUserInfo(HttpServletRequest request,UserWithBLOBs user, String[] hobbys, CommonsMultipartFile file) {
		if (user==null) {
			return Result.createError("参数错误");
		}
		
		if (!ArrayUtils.isEmpty(hobbys)) {
			String temp="";
			for (String str : hobbys) {
				temp+=str+",";
			}
			user.setHobby(temp);
		}
		FileResult fs=FileUtil.saveFileToPath(request, file, BlogConstants.WECHAT_SAVE_PATH, BlogConstants.WECHAT_MAPPING_URL);
		if (fs!=null) {
			user.setWechat(fs.getHttpUrl());
		}
		UserWithBLOBs loginUser=(UserWithBLOBs) request.getSession().getAttribute(BlogConstants.LOGIN_SESSION_KEY);
		user.setUuid(loginUser.getUuid());
		String originUrl=loginUser.getWechat();
		
		if(userMapper.updateByPrimaryKeySelective(user)>0){
			FileUtil.deleteFile(request, originUrl, BlogConstants.WECHAT_SAVE_PATH);
			loginUser=userMapper.selectByPrimaryKey(loginUser.getUuid());
			loginUser.setPassword(null);
			request.getSession().setAttribute(BlogConstants.LOGIN_SESSION_KEY,loginUser);
			return Result.createSuccess("修改成功");
		}
		if (fs!=null&&fs.getFile().exists()) {
			fs.getFile().delete();
		}
		
		return Result.createError("修改失败");
	}

	
	@Override
	public Result updateUserPhoto(HttpServletRequest request, CommonsMultipartFile file) {
				
		UserWithBLOBs loginUser=(UserWithBLOBs) request.getSession().getAttribute(BlogConstants.LOGIN_SESSION_KEY);
		
		String originUrl=loginUser.getPhotourl();
		
		FileResult fs=FileUtil.saveFileToPath(request, file, BlogConstants.PHOTO_SAVE_PATH, BlogConstants.PHOTO_MAPPING_URL);
		if (fs!=null) {
			loginUser.setPhotourl(fs.getHttpUrl());
		}
		if(userMapper.updateByPrimaryKeySelective(loginUser)>0){
			request.getSession().setAttribute(BlogConstants.LOGIN_SESSION_KEY,loginUser);
			FileUtil.deleteFile(request, originUrl, BlogConstants.PHOTO_SAVE_PATH);
			return Result.createSuccess("头像修改成功");
		}
		
		return Result.createError("头像修改失败");
	}

	//密码修改
	@Override
	public Result updatepass(String originPass, String pass, String repass,HttpServletRequest request) {
		if (StringUtils.isBlank(originPass)||StringUtils.isBlank(pass)||StringUtils.isBlank(repass)) {
			return Result.createError("参数错误");		
		}
		Result<String> rs=checkEncode(originPass);
		if (rs.isSuccess())originPass=rs.getData();else return rs;
		
		rs=checkEncode(pass);
		if (rs.isSuccess())pass=rs.getData();else return rs;
		
		rs=checkEncode(repass);
		if (rs.isSuccess())repass=rs.getData();else return rs;
		
		if (!pass.equals(repass)) {
			return Result.createError("两次密码不一致");
		}
		
		UserWithBLOBs loginUser=(UserWithBLOBs) request.getSession().getAttribute(BlogConstants.LOGIN_SESSION_KEY);
		loginUser=userMapper.selectByPrimaryKey(loginUser.getUuid());
		if (!loginUser.getPassword().equals(originPass)) {
			return Result.createError("原密码输入有误");
		}
		loginUser.setPassword(pass);
		if(userMapper.updateByPrimaryKeySelective(loginUser)>0){
			return Result.createSuccess("密码修改成功");
		}
		
		
		return Result.createSuccess("密码修改失败");
	}
	
	
	
	
	
	/**
	 * 检查密码有效性
	 * @param pass
	 * @return
	 */
	private Result<String> checkEncode(String pass){
		//解密
		String str=new String(Base64.getDecoder().decode(pass));
		if (!str.contains("-")) {
			return Result.createError("非法操作");	
		}
		
		String strs[]=str.split("-");
		if (!TokenUtil.encodeStr.equals(strs[1])) {
			return Result.createError("非法操作");
		}
		return Result.createSuccess(MD5.md5(strs[0].getBytes()),"");
	}

}
