package com.rongyi.nasdaq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlatformController extends BaseController {

	@RequestMapping("/platform.htm")
    public ModelAndView platform() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(NAVSELECTED, 5);
    	return modelAndView;
    	
    }
	
}
