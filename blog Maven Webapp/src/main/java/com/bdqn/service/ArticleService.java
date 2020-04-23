package com.bdqn.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bdqn.entity.Article;
import com.bdqn.entity.ArticleCheck;
import com.bdqn.entity.ArticleWithBLOBs;
import com.bdqn.untity.Page;
import com.bdqn.untity.Result;
/**
 * 文章业务
 * @author RP
 *
 */
public interface ArticleService {
	
	//流加载分页
	public Result<Page<Article>> getPageArticle(Integer tid,Integer pageIndex,Integer pageSize);
	
	//文章列表的分页
	public Result<Page<Article>> getPageArticles(String timeRange,String title,Integer pageIndex,Integer pageSize,HttpSession session,boolean isDraft);
	
	//上傳文件中的图片
	public Map<String, Object> uploadImg(HttpServletRequest request,CommonsMultipartFile file);
	
	
	public Result  editArticle(String flag,ArticleWithBLOBs article,CommonsMultipartFile file,HttpServletRequest request);
	
	
	//主键查看
	public ArticleWithBLOBs  getArticleById(String uuid);
	
	public 	List<ArticleCheck> getArticleCheck(String uuid);
} 
