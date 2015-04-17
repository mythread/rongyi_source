package com.rongyi.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.bean.User;
import com.rongyi.cms.dao.UserDao;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private UserDao userdao;
	
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		System.out.println(userName);
		User user = null;
		try {
			user = userdao.findUserByName(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
