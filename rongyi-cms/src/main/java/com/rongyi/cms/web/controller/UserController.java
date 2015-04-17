
package com.rongyi.cms.web.controller;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import base.util.crypt.MD5Encryption;

import com.rongyi.cms.bean.User;
import com.rongyi.cms.service.UserService;

/**
 * @author ShaoYanbin
 * @用户登录和注销
 * */
@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/userIndex", method = RequestMethod.GET) 
	public String printWelcome(ModelMap model, Principal principal ) {
		 
		String name = principal.getName();
		model.addAttribute("name", name);
		return "adzones/index";
 
	}
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model, String error) {
		model.addAttribute("error", error);
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
 
		return "login";
 
	}	
	
	//跳转到修改密码界面
	@RequestMapping(value="/setPasswd", method = RequestMethod.GET)
	public String Password(ModelMap model) {
 
		return "/setpassword";
 
	}
	
	@RequestMapping(value="/changePassword")
	public String changePassword(ModelMap model,Principal principal,HttpServletRequest request, HttpServletResponse response) {
		
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		String name = principal.getName();
		User user = userService.findUserByName(name);
		if(null!=oldpassword)
		oldpassword=MD5Encryption.encrypt(oldpassword);
		if(user.getPassword().equals(oldpassword)){
			
			if(null!=newpassword)
			user.setPassword(MD5Encryption.encrypt(newpassword));
		}else{
			model.addAttribute("message", "原密码不正确！");
			return "/setpassword";
		}
		userService.update(user);
		return "login";
		
	}
	
	@RequestMapping(value="/save")
	public String save(ModelMap model,User user){
		user.setName("admin");
		user.setPassword(MD5Encryption.encrypt("admin"));
		user.setEmail("admin@qq.com");
		user.setPhone("110");
		user.setMallId("1");
		user.setCreateAt(new Date(System.currentTimeMillis()));
		try {
			userService.save(user);
			model.addAttribute("saveMessage", "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "adzones/index";
	}
}
