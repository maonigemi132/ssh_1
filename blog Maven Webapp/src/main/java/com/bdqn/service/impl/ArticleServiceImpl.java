package com.bdqn.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bdqn.component.ArticleStatus;
import com.bdqn.component.BlogConstants;
import com.bdqn.component.SensitivewordFilter;
import com.bdqn.dao.ArticleCheckMapper;
import com.bdqn.dao.ArticleMapper;
import com.bdqn.entity.Article;
import com.bdqn.entity.ArticleCheck;
import com.bdqn.entity.ArticleWithBLOBs;
import com.bdqn.entity.UserWithBLOBs;
import com.bdqn.service.ArticleService;
import com.bdqn.thread.SensitivewordFilterThead;
import com.bdqn.untity.FileResult;
import com.bdqn.untity.FileUtil;
import com.bdqn.untity.Page;
import com.bdqn.untity.Result;

@Service("/articleService")
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private ArticleCheckMapper articleCheckMapper;
	//敏感词注入
	@Autowired
	private SensitivewordFilter sensitivewordFilter;
	
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	@Override
	public Result<Page<Article>> getPageArticle(Integer tid, Integer pageIndex, Integer pageSize) {
		Page<Article> page=new  Page<Article>(pageIndex,pageSize);
		page.setCount(articleMapper.selectCount(tid, 0));
		page.setDataList(articleMapper.selectList(tid, 0, (pageIndex-1)*pageSize, pageSize));
		
		return Result.createSuccess(page);
	}

	@Override
	public Map<String, Object> uploadImg(HttpServletRequest request, CommonsMultipartFile file) {
		
		String newFileName=request.getSession().getId()+"_article";
		
		FileResult fs=FileUtil.saveFileToPath(request, file, BlogConstants.ARTICLE_SAVE_PATH, BlogConstants.ARTICLE_MAPPING_URL,newFileName);
		Map<String, Object> map=new HashMap<String, Object>();
		if (fs!=null) {
			
			List<String> list=(List<String>) request.getSession().getAttribute("tempArticleFname");
			if(list==null) list=new ArrayList<String>();
			list.add(fs.getFileName());
			request.getSession().setAttribute("tempArticleFname",list);
			
			
			map.put("code", 0);
			Map<String, Object> map1=new HashMap<String, Object>();
			map1.put("src", fs.getHttpUrl());
			map.put("data", map1);
		}else {
			map.put("code", 1);
			map.put("msg", "图片上传失败");
		}			
		return map;
	}

	@Override
	public Result editArticle(String flag, ArticleWithBLOBs article, CommonsMultipartFile file,
			HttpServletRequest request) {
		
		
		FileResult rs=null;
		if (!Arrays.asList("status","delete").contains(flag)) {	
			if(file!=null)
			rs=FileUtil.saveFileToPath(request, file, BlogConstants.ARTICLE_SAVE_PATH, BlogConstants.ARTICLE_MAPPING_URL);
			
			
			if (rs==null&&!"update".equals(flag)) {
				return Result.createError("博客发布失败，无法保存图片");
			}
		}
		UserWithBLOBs loginUser=(UserWithBLOBs) request.getSession().getAttribute(BlogConstants.LOGIN_SESSION_KEY);
		
		if ("add".equals(flag)||"update".equals(flag)) {
			if (article==null||StringUtils.isBlank(article.getTitle())||StringUtils.isBlank(article.getSummary())||article.getArticletypeid()==null) {
				return Result.createError("参数错误");
			}
			if ("update".equals(flag)&&StringUtils.isBlank(article.getUuid())) {
				return Result.createError("参数错误");
			}

		if(rs!=null)
		article.setCover(rs.getHttpUrl());
		if("add".equals(flag)){
			article.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			article.setAwesome(0);
			article.setReader(0);
			article.setCreatetime(new Date());
			article.setUserId(loginUser.getUuid());
		}	
		
		Integer originStatus=article.getStatus();//原本状态
		article.setStatus(ArticleStatus.IN_AUDIT.getValue());//设置成待审核
		
		int count="add".equals(flag)?articleMapper.insertSelective(article):articleMapper.updateByPrimaryKeySelective(article);
		
		if (count>0) {
			request.getSession().removeAttribute("tempArticleFname");
			//准备审核
			SensitivewordFilterThead thead=new SensitivewordFilterThead
			(sensitivewordFilter, article, originStatus, articleCheckMapper, articleMapper, transactionManager);
			thead.start();
			
			return Result.createSuccess("操作成功 审核通过后才会显示");
		}
		if(rs.getFile().exists())rs.getFile().delete();	
		return Result.createError("操作失败");
		
		
		
		}else if ("cover".equals(flag)) {//修改封面
			if (article==null||article.getUuid()==null) {
				return Result.createError("参数错误");
			}
			article.setCover(rs.getHttpUrl());
			//以后需要添加当前登录的是管理员 修改则不需要验证
			if (articleMapper.selectCountByIdAndUserId(article.getUuid(), loginUser.getUuid())==0) {
				return Result.createError("您没有操作权限");
			}
			
			if (articleMapper.updateByPrimaryKeySelective(article)>0) {
				return Result.createSuccess("修改封面成功");
			}
			return Result.createError("修改封面失败");
			
		}else if ("status".equals(flag)) {//状态重置
			if (article==null||article.getUuid()==null||article.getStatus()==null) {
				return Result.createError("参数错误");
			}
			//以后需要添加当前登录的是管理员 修改则不需要验证
			if (articleMapper.selectCountByIdAndUserId(article.getUuid(), loginUser.getUuid())==0) {
				return Result.createError("您没有操作权限");
			}
			
			Integer originStatus=article.getStatus();//原本状态
			if (articleMapper.updateByPrimaryKeySelective(article)>0) {
				ArticleWithBLOBs newarticle=articleMapper.selectByPrimaryKey(article.getUuid());
				//准备审核
				SensitivewordFilterThead thead=new SensitivewordFilterThead
				(sensitivewordFilter, newarticle, originStatus, articleCheckMapper, articleMapper, transactionManager);
				thead.start();
				return Result.createSuccess("状态重置成功,等待审核！");
			}else {
				return Result.createError("状态重置失败");
			}
			
			
			
			
		}else if ("delete".equals(flag)) {
			if (StringUtils.isBlank(article.getUuid())) {
				return Result.createError("参数错误");
			}
			article.setStatus(ArticleStatus.DRAFTS.getValue());
			if (articleMapper.updateByPrimaryKeySelective(article)>0) {
				return Result.createSuccess("删除成功,可以在草稿箱中恢复文章");
			}
			return Result.createError("删除失败");		
		}		
		return Result.createError("非法操作");		
	}

	@Override
	public Result<Page<Article>> getPageArticles(String timeRange, String title,Integer pageIndex,
			Integer pageSize, HttpSession session,boolean isDraft) {
		
		UserWithBLOBs loginUser=(UserWithBLOBs) session.getAttribute(BlogConstants.LOGIN_SESSION_KEY);
		Page<Article> page=new  Page<Article>(pageIndex,pageSize);
		Map<String, Object> param=new HashMap<String, Object>();
		if (StringUtils.isNotBlank(timeRange)) {
			param.put("startTime", timeRange.split("~")[0].trim());
			param.put("endTime", timeRange.split("~")[1].trim());
		}			
		param.put("title",title);
		param.put("userId",loginUser.getUuid());
		
		if (isDraft) {
			param.put("statusList", Arrays.asList(ArticleStatus.DRAFTS.getValue()));
		}else {
			param.put("statusList", ArticleStatus.getNotDraftStatus());
		}
		
		
		page.setCount(articleMapper.selectCount1(param));
		
		param.put("start", (pageIndex-1)*pageSize);
		param.put("row", pageSize);
		
		page.setDataList(articleMapper.selectList1(param));
		
		return Result.createSuccess(page);
	}

	@Override
	public ArticleWithBLOBs getArticleById(String uuid) {	
		return articleMapper.selectByPrimaryKey(uuid);
	}

	@Override
	public List<ArticleCheck> getArticleCheck(String uuid) {	
		return articleCheckMapper.selectListByArticleId(uuid);
	}

	

}
