package com.bdqn.dao;

import com.bdqn.entity.Setting;
import com.bdqn.entity.SettingWithBLOBs;

public interface SettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SettingWithBLOBs record);

    int insertSelective(SettingWithBLOBs record);

    SettingWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SettingWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SettingWithBLOBs record);

    int updateByPrimaryKey(Setting record);
}