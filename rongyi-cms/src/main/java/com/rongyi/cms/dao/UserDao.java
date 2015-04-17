package com.rongyi.cms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.User;
import com.rongyi.cms.mapper.UserMapper;

@Repository
public class UserDao {
	
	@Autowired
	private UserMapper userMapper;
	
	public User findUserByName(String name){
		
		User user = userMapper.findUserByName(name);
		return user;
	}
	
	public int save(User user){
		
		return userMapper.insert(user);
	}
	
	public int update(User user){
		
		return userMapper.updateByPrimaryKey(user);
	}
}
