package com.rongyi.cms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.User;
import com.rongyi.cms.mapper.UserMapper;

@Repository
public class DemoDao {
	
	@Autowired
	private UserMapper userMapper;
	
	
	public void addDemo() throws Exception {
		User user = new User();
		user.setName("1231231");
		userMapper.insert(user);
		}
}
