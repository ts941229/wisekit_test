package com.wisekit.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wisekit.test.repository.MemberRepository;
import com.wisekit.test.service.EventService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("/event-form")
	public String eventForm() {
		return "/event/eventForm";
	}
	
	@GetMapping("/event-create")
	public String eventCreate() { 
		
		eventService.eventCreate(); // 응모 이벤트를 조회하고 없으면 생성해주는 메소드
		
		return "redirect:/";
	}
	
	@GetMapping("/event-entry")
	public String eventEntry() {
		
		eventService.eventEntry();
		
		return "redirect:/event/event-form";
	}
	
	@GetMapping("/event-end")
	public String endEventOfTheDay() {
		eventService.endEventOfTheDay();

		return "redirect:/member/admin/admin-form";
	}

	@GetMapping("/event-winner-form")
	public String eventWinnerForm(Model model) {
		
		eventService.eventWinnerRender(model);
		
		return "/event/eventWinnerForm";
	}
	
}
