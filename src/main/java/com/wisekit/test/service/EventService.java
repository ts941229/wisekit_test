package com.wisekit.test.service;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisekit.test.model.Entry;
import com.wisekit.test.model.Event;
import com.wisekit.test.model.Member;
import com.wisekit.test.model.RemainingWinner;
import com.wisekit.test.repository.EntryRepository;
import com.wisekit.test.repository.EventRepository;
import com.wisekit.test.repository.MemberRepository;
import com.wisekit.test.repository.RemainingWinnerRepository;
import com.wisekit.testglobal.util.EventUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private EntryRepository entryRepository;
	
	@Autowired
	private RemainingWinnerRepository remainingWinnerRepository;
	
	// 이벤트 최초 생성 메서드
	public void eventCreate() {
		
		if(eventRepository.findById((long) 1).isEmpty()) {
			
			System.out.println("이벤트를 생성합니다.");
			
			int period = 15;
			LocalDate start_date = LocalDate.now(); // 시작날짜 : 오늘
			LocalDate end_date = start_date.plusDays((period-1)); // 종료날짜 : 오늘 + 이벤트기간 (시작날짜도 포함이기에 -1해줌)
			Event event = Event.builder()
										.name("응모이벤트")
										.period(period)
										.start_date(EventUtil.getInstance().dateFormat(start_date))
										.end_date(EventUtil.getInstance().dateFormat(end_date))
										.build();
			
			eventRepository.save(event);
			
			// 현재는 정적인 값을 넣었지만 원한다면 총 당첨자 수 또한 화면단에서 받아 사용할 수도 있다
			RemainingWinner remainingWinner = RemainingWinner.builder()
																							.event(event)
																							.first_place(50)
																							.second_place(250)
																							.third_place(500)
																							.fourth_place(10000)
																							.build();
			remainingWinnerRepository.save(remainingWinner);
			
		}
		
	}

	// 이벤트 응모 메서드
	public void eventEntry() {

		// 해당 이벤트가 존재할 경우
		if(!eventRepository.findById((long)1).isEmpty()) {
			
			// 현재 프로젝트엔 로그인, 회원가입을 구현하지 않아서 임의로 멤버 생성 후 insert
			Member member = Member.builder()
					.name("테스트유저")
					.build();
			memberRepository.save(member); 
			
			// 응모 엔티티 생성
			Entry entry = Entry.builder()
					.member(member)
					.event(eventRepository.findById((long)1).get())
					.entry_date(EventUtil.getInstance().dateFormat(LocalDate.now()))
					.build();
			entryRepository.save(entry);
		}
	}
	
	// 당일 이벤트 종료 메서드
	// 여기선 관리자가 직접 종료시키지만 실제 구현 시 특정 시간 (ex : 밤10시)에 종료시키도록 구현할 예정
	// 당일 이벤트 종료에 따라 응모자 수를 파악하고 당첨자를 결정해야 한다
	public void endEventOfTheDay() {
		
		// 당첨자를 결정하기 전, 등수 별 하루 평균 당첨자를 먼저 구해야 한다
		// 하루 평균 당첨자 = 남은 당첨자 / 남은 기간
		
		// 남은 당첨자 entity
		RemainingWinner rw = remainingWinnerRepository.findById((long) 1).get(); // 현 프로젝트에선 이벤트가 하나뿐이니 id=1 많아지면 event_id로 찾아야함
		
		String end_date = eventRepository.findById((long)1).get().getEnd_date();
		String today = EventUtil.getInstance().dateFormat(LocalDate.now());
		
		// 남은 기간
		int rd = EventUtil.getInstance().getRemainingDuration(today, end_date);
		
		if(rd<1) {rd=1;} // 남은기간이 음수이면 안되기 때문에
		
		// 등수별 당일 평균 당첨자
		int evg_first = EventUtil.getInstance().getDailyEverage(rw.getFirst_place(), rd);
		int evg_second = EventUtil.getInstance().getDailyEverage(rw.getSecond_place(), rd);
		int evg_third = EventUtil.getInstance().getDailyEverage(rw.getThird_place(), rd);
		
		System.out.println("evg_first : "+evg_first);
		System.out.println("evg_second : "+evg_second);
		System.out.println("evg_third : "+evg_third);
		
		// 1, 2, 3등의 당일 평균 당첨자 만큼 당첨자를 선별하고, 나머지 응모자는 4등처리
		// 추첨은 당일 총 응모자수를 구한 후, 확률을 조정하여 당첨자를 선별한다
		
		// 당일 총 응모자 수
		int daily_total_entry = (int) entryRepository.countByEntryDateAndEventId(today, (long) 1);
		
		System.out.println("당일 총 응모자 수 :"+ daily_total_entry);
		
		
		
	}
	
}
