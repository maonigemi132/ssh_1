package com.bdqn.dao;

import com.bdqn.entity.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}