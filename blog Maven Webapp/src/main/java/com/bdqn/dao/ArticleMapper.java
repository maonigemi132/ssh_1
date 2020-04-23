package com.bdqn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bdqn.entity.Article;
import com.bdqn.entity.ArticleWithBLOBs;

public interface ArticleMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(ArticleWithBLOBs record);

    int insertSelective(ArticleWithBLOBs record);

    ArticleWithBLOBs selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(ArticleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleWithBLOBs record);

    int updateByPrimaryKey(Article record);
    
    
    int selectCount(@Param("tid")Integer tid,@Param("type")Integer type);
    
    int selectCount1(Map<String, Object> param);

    
    List<Article> selectList(@Param("tid")Integer tid,@Param("type")Integer type,@Param("start")Integer start,@Param("row")Integer row);
    
    List<Article> selectList1(Map<String, Object> param);
    
    int selectCountByIdAndUserId(@Param("uuid")String uuid,@Param("userId")String userId);
    
    
}