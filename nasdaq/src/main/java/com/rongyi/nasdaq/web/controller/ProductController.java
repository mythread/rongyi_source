package com.rongyi.nasdaq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

	
	@RequestMapping("/terminal.htm")
    public ModelAndView terminal() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(NAVSELECTED, 2);
		modelAndView.addObject(PRODUCTNAVSELECTED, 1);
    	return modelAndView;
    	
    }
	
	@RequestMapping("/app.htm")
    public ModelAndView app() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(NAVSELECTED, 2);
		modelAndView.addObject(PRODUCTNAVSELECTED, 2);
    	return modelAndView;
    	
    }
	
}
