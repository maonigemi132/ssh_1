package com.bdqn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.HeaderTokenizer.Token;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdqn.component.BlogConstants;
import com.bdqn.entity.BannerWithBLOBs;
import com.bdqn.service.ArticleTypeService;
import com.bdqn.service.BannerService;
import com.bdqn.untity.KaptchaUtil;
import com.bdqn.untity.ResponseUtil;
import com.bdqn.untity.Result;
import com.bdqn.untity.StatusType;
import com.bdqn.untity.TokenUtil;

@Controller
public class CurrentController {

	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private ArticleTypeService articleService;
	
	@GetMapping("/client/{view}.html")
	public String view(@PathVariable("view")String view,Model model,HttpSession session){
		session.setAttribute("token", TokenUtil.getToKen());
		if (view.equals("home")) {//主页的请求
			//加载主页数据
			Result<List<BannerWithBLOBs>> rs=bannerService.getBannser(null, StatusType.AVAILABLE);
			if (rs.isSuccess()) {
				model.addAttribute("banners",rs.getData());
			}else {
				model.addAttribute("msg",rs.getContent());
			}
			//博客类型
			model.addAttribute("articleTypes",articleService.getAllArticleType());
		}else if (view.equals("user-center")||view.equals("user-blog")) {
			model.addAttribute("articleTypes",articleService.getAllArticleType());
		}
		return "client/"+view;
	}
	
	/**
	 * 获取加密字符串
	 * @param token
	 * @param session
	 * @return
	 */
	@GetMapping("/getEncode")
	@ResponseBody
	public Map<String, Object> getEncode(String token,HttpSession session){
		Map<String, Object> map=new HashMap<String, Object>();
		String sessionToken=(String) session.getAttribute("token");
		if (StringUtils.isBlank(token)||StringUtils.isBlank(sessionToken)||!sessionToken.equals(token)) {
			map.put("error", "非法请求");
		}else{			
			map.put("code", TokenUtil.encodeStr);
		}
		return map;
	}
	
	//验证码
	@GetMapping("/kaptcha")
	public void kaptcha(HttpServletRequest request,HttpServletResponse response){
		KaptchaUtil.responseImg(request, response);
		
	}
	
	@GetMapping("/loginsLive")
	@ResponseBody
	public Result loginsLive(HttpSession session){
		Object object=session.getAttribute(BlogConstants.LOGIN_SESSION_KEY);
		if (object==null) {
			return Result.createError("登录已失效");
		}
		return Result.createSuccess("登录未失效");
	}
}
