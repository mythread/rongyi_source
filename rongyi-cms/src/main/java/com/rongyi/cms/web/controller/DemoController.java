package com.rongyi.cms.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongyi.cms.service.DemoService;

@Controller
@RequestMapping("/demo") 
public class DemoController {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired(required = true)
	private DemoService demoService;
	
	
    @RequestMapping(value = "/index")
	public String viewUser(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
    	demoService.addDemo();
    	
		return "demo/index";
	}
    
}
