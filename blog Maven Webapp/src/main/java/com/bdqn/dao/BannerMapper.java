package com.bdqn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import com.bdqn.entity.Banner;
import com.bdqn.entity.BannerWithBLOBs;

public interface BannerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BannerWithBLOBs record);

    int insertSelective(BannerWithBLOBs record);

    BannerWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BannerWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BannerWithBLOBs record);

    int updateByPrimaryKey(Banner record);
    
    List<BannerWithBLOBs> selectListBanner(@Param("uid")String uid,@Param("status")Integer status);
}