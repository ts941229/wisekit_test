package com.wisekit.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
public class EventController {

	@GetMapping("/event-form")
	public String eventForm() {
		System.out.println("ㅇㅇ");
		return "/event/eventForm";
	}
	
}
