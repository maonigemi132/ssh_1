package com.bdqn.component;

import java.io.File;

/**
 * 系统中的常量
 * @author RP
 *
 */
public class BlogConstants {

	/**
	 * 登录存储在session的key
	 * 
	 */
	public static final String LOGIN_SESSION_KEY="loginUser";
	
	
	/**
	 * 微信二维码
	 */
	public static final String WECHAT_MAPPING_URL="static/client/img/wechat/";
	

	/**
	 * 微信二维码保存的相对路径
	 */
	public static final String WECHAT_SAVE_PATH="static"+File.separator+"client"+File.separator+"img"+File.separator+"wechat";
	
	
	/**
	 * 用户头像的映射地址
	 */
	public static final String PHOTO_MAPPING_URL="static/client/img/photo/";
	
	
	/**
	 * 用户头像的保存地址
	 */
	public static final String PHOTO_SAVE_PATH="static"+File.separator+"client"+File.separator+"img"+File.separator+"photo";
	
	
	/**
	 * 文章图片的映射地址
	 */
	public static final String ARTICLE_MAPPING_URL="static/client/img/article/";
	
	
	/**
	 * 文章图片的保存地址
	 */
	public static final String ARTICLE_SAVE_PATH="static"+File.separator+"client"+File.separator+"img"+File.separator+"article";

}