package com.wisekit.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String index() {
		
		System.out.println("index controller");
		return "index";
	}
	
}
