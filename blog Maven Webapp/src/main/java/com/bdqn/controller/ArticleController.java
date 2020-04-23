package com.bdqn.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bdqn.entity.Article;
import com.bdqn.entity.ArticleWithBLOBs;
import com.bdqn.service.ArticleService;
import com.bdqn.service.ArticleTypeService;
import com.bdqn.untity.Page;
import com.bdqn.untity.Result;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	//分页加载文章
	@GetMapping("/flowArticle")
	@ResponseBody
	public Page<Article> flowArticle(@RequestParam(required=true)Integer tid,
			@RequestParam(defaultValue="1")Integer pageIndex,@RequestParam(defaultValue="8")Integer pageSize){
		
		
		return articleService.getPageArticle(tid, pageIndex, pageSize).getData();
	}
	
	
	
	//分页加载文章
		@GetMapping("/{isDraft}-listArticles")
		@ResponseBody
		public Page<Article> listArticle(String timeRange,String title,@RequestParam(defaultValue="1")Integer pageIndex,@RequestParam(defaultValue="5")Integer pageSize,HttpSession session,@PathVariable("isDraft")String isDraft){			
			return articleService.getPageArticles(timeRange, title, pageIndex, pageSize, session,Boolean.valueOf(isDraft)).getData();
		}
	
		
	//富文本编辑器图片上传
	@PostMapping("/articleUploadImg")
	@ResponseBody
	public Map<String, Object> articleUploadImg(HttpServletRequest request,@RequestParam(name="file",required=false)CommonsMultipartFile file){
		return	articleService.uploadImg(request, file);

	}
	/**
	 * 增删改
	 * @return
	 */
	@PostMapping("/{flag}-article")
	@ResponseBody
	public Result editArticle(@PathVariable("flag")String flag,ArticleWithBLOBs article,
			@RequestParam(name="upload",required=false)CommonsMultipartFile file,HttpServletRequest request){
		
		
		return articleService.editArticle(flag, article, file, request);
	}
	
	
	//查看
	@GetMapping("/{flag}-find")
	public String findArticle(@PathVariable("flag")String flag,String uuid,Model model){
		
		model.addAttribute("article", articleService.getArticleById(uuid));
		model.addAttribute("flag",flag);
		//推荐博客类型
		model.addAttribute("articleTypes",articleTypeService.getAllArticleType());
		return "client/user-editblog";
	}
	
}
