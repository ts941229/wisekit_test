package com.wisekit.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping("/admin/admin-form")
	public String adminForm() {
		return "/member/admin/adminForm";
	}
	
}
