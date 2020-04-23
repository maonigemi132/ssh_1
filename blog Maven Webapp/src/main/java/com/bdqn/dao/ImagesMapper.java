package com.bdqn.dao;

import com.bdqn.entity.Images;

public interface ImagesMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKeyWithBLOBs(Images record);

    int updateByPrimaryKey(Images record);
}