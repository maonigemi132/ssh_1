package com.bdqn.dao;

import java.util.List;

import com.bdqn.entity.Articletype;

public interface ArticletypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Articletype record);

    int insertSelective(Articletype record);

    Articletype selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Articletype record);

    int updateByPrimaryKey(Articletype record);
    
    
    List<Articletype> selectList();
}