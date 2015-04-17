package com.rongyi.nasdaq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileNotFoundController extends BaseController {

	@RequestMapping(value = "/404.htm")
    public String fileNotFound() {
    	return "404";
    }
	
}
