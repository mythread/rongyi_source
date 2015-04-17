package com.rongyi.cms.mapper;

import java.util.List;

import com.rongyi.cms.bean.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User findUserByName(String name);
    
    List<User> selectAll(String id);
}