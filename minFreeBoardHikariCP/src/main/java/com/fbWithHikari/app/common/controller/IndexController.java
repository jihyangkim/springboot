package com.fbWithHikari.app.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String rootIndex(Model model) {
		
		model.addAttribute("sss","sssvalue");
		
		return "index";
	}
}
