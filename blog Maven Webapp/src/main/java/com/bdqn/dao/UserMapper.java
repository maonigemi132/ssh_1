package com.bdqn.dao;

import com.bdqn.entity.User;
import com.bdqn.entity.UserWithBLOBs;

public interface UserMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(UserWithBLOBs record);

    int insertSelective(UserWithBLOBs record);

    UserWithBLOBs selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(UserWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserWithBLOBs record);

    int updateByPrimaryKey(User record);
    
    
    //根据邮箱统计
    int selectCountUserEmail(String email);
    
    //登录查询
    UserWithBLOBs selectUserByEmialPass(UserWithBLOBs user);

}