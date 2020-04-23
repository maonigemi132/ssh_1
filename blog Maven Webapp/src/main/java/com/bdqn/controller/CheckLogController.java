package com.bdqn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bdqn.entity.ArticleCheck;
import com.bdqn.service.ArticleService;

@RequestMapping("/check")
@Controller()
public class CheckLogController {
	
	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/findLog")
	public String findLog(String uuid,Model model){
		 model.addAttribute("logs", articleService.getArticleCheck(uuid));
		 return "client/log";
	}
}
