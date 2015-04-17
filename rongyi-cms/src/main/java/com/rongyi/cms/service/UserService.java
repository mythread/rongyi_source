package com.rongyi.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.bean.User;
import com.rongyi.cms.dao.UserDao;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;

	public void save(User user) throws Exception{
		userDao.save(user);
	}
	
	public void update(User user) {
		userDao.update(user);
	}
	
	public User findUserByName(String name) {
		
		return userDao.findUserByName(name);
	}
}
