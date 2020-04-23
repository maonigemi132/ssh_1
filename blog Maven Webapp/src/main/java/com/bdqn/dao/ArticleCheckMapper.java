package com.bdqn.dao;

import java.util.List;

import com.bdqn.entity.ArticleCheck;

public interface ArticleCheckMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(ArticleCheck record);

    int insertSelective(ArticleCheck record);

    ArticleCheck selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(ArticleCheck record);

    int updateByPrimaryKey(ArticleCheck record);
    
    
    List<ArticleCheck> selectListByArticleId(String uuid);
}