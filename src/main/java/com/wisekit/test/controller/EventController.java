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
		
		// 응모와 동시에 유저 생성 , 해당 유저에 대한 응모정보 생성
		for(int i=0; i<100; i++) {
			// 100명 응모
			eventService.eventEntry();
		}
		 
		
		return "redirect:/event/event-form";
	}
	
	@GetMapping("/event-end")
	public String endEventOfTheDay() {
		eventService.endEventOfTheDay(); // 당일 응모 종료시 처리해야 할 것들 처리해주는 메소드

		return "redirect:/member/admin/admin-form";
	}

	@GetMapping("/event-winner-form")
	public String eventWinnerForm(Model model) {
		
		eventService.eventWinnerRender(model); // 화면단에 당첨자 넘겨주는 메소드
		
		return "/event/eventWinnerForm";
	}
	
}
