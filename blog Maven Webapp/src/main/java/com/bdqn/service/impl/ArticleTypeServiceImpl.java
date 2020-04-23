package com.bdqn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdqn.dao.ArticletypeMapper;
import com.bdqn.entity.Articletype;
import com.bdqn.service.ArticleTypeService;

@Service("articleService")
public class ArticleTypeServiceImpl implements ArticleTypeService{

	@Autowired
	private ArticletypeMapper articletypeMapper;
	
	@Override
	public List<Articletype> getAllArticleType() {

		return articletypeMapper.selectList();
	}

}
