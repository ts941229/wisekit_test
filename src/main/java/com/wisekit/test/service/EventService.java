package com.wisekit.test.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.wisekit.test.global.util.EventUtil;
import com.wisekit.test.global.util.Util;
import com.wisekit.test.model.Entry;
import com.wisekit.test.model.Event;
import com.wisekit.test.model.Member;
import com.wisekit.test.model.RemainingWinner;
import com.wisekit.test.model.TodayWinner;
import com.wisekit.test.model.Winner;
import com.wisekit.test.repository.EntryRepository;
import com.wisekit.test.repository.EventRepository;
import com.wisekit.test.repository.MemberRepository;
import com.wisekit.test.repository.RemainingWinnerRepository;
import com.wisekit.test.repository.TodayWinnerRepository;
import com.wisekit.test.repository.WinnerRepository;

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
	
	@Autowired
	private WinnerRepository winnerRepository;
	
	@Autowired
	private TodayWinnerRepository todayWinnerRepository;
	
	// 이벤트 최초 생성 메서드
	public void eventCreate() {
		
		// 해당 이벤트가 존재하지 않을때 실행
		if(eventRepository.findById((long) 1).isEmpty()) {
			
			System.out.println("이벤트를 생성합니다.");
			
			int period = 15; // 총 이벤트 기간
			LocalDate start_date = LocalDate.now(); // 시작날짜 : 오늘
			LocalDate end_date = start_date.plusDays((period-1)); // 종료날짜 : 오늘 + 이벤트기간 (시작날짜도 포함이기에 -1해줌)
			Event event = Event.builder()
										.name("응모이벤트")
										.period(period)
										.start_date(Util.getInstance().dateFormat(start_date))
										.end_date(Util.getInstance().dateFormat(end_date))
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
	public int eventEntry() {
		
		int rank = 0;
		
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
					.entry_date(Util.getInstance().dateFormat(LocalDate.now()))
					.build();
			entryRepository.save(entry);
			
			rank = getRank(entry);
		}
		
		return rank;

	}
	
	public int getRank(Entry entry) {
		// 응모와 동시에 당첨 여부 , 등수를 선별해야 한다
		
		// solution
		// 1. 하루 평균 당첨자의 합을 구한다
		// 2. 해당 응모 회원의 행운 번호 추첨 (1 ~ 하루 평균 당첨가능자의 합)
		// 3. 등수별 하루 평균 당첨자들의 합 (1, 2, 3등) 만큼의 당첨 번호를 랜덤으로 뽑는다.
		//     번호 순서대로 1~3등 당첨번호로 지정
		// 4. 응모자의 행운 번호와 당첨번호 비교
		// 5. 당첨 번호와 맞는게 있다면 해당 등수로 선정, 없으면 4등

		
		// 하루 평균 당첨자의 합 구하기
		
		// 남은 당첨자 entity
		RemainingWinner rw = remainingWinnerRepository.findById((long) 1).get();
		
		String today = Util.getInstance().dateFormat(LocalDate.now());
		String end_date = eventRepository.findById((long)1).get().getEnd_date();

		// 오늘의 당첨자 entity
		TodayWinner todayWinner = todayWinnerRepository.findByTodayWinnerDate(today);
		if(todayWinner==null) {
			todayWinner = TodayWinner.builder()
													.today_winner_date(today)
													.build();
			
			todayWinnerRepository.save(todayWinner);
		}
		
		int today_first = todayWinner.getFirst_place();
		int today_second = todayWinner.getSecond_place();
		int today_third = todayWinner.getThird_place();
		
		// 남은 기간
		int rd = EventUtil.getInstance().getRemainingDuration(today, end_date);

		if(rd<1) {rd=1;} // 남은기간이 음수이면 안되기 때문에
		
		// 등수별 당일 평균 당첨자
		int avg_first = EventUtil.getInstance().getDailyEverage(rw.getFirst_place(), rd);
		int avg_second = EventUtil.getInstance().getDailyEverage(rw.getSecond_place(), rd);
		int avg_third = EventUtil.getInstance().getDailyEverage(rw.getThird_place(), rd);
		
		// 등수별 하루 평균 당첨자들의 합
		int sum_place = avg_first + avg_second + avg_third;
		
		// 행운 번호
		int lucky_number = (int) (Math.random()*sum_place) + 1;
		
		// 오늘의 당첨번호가 들어있는 배열 가져오기 (이전에 만들어 놓은 함수 이용)
		int[] win_numbers = EventUtil.getInstance().getDailyTotalWinner(sum_place);
		
		boolean isWin = false;
		int rank = 4; // 당첨되지 않을 시 4등
		

		// 행운 번호와 당첨 번호 비교
		for(int i=0; i<win_numbers.length; i++) {
			
			// 행운 번호가 당첨 번호중에 있다면
			if(lucky_number==win_numbers[i]) {
				// 당첨됨 (4등은 아니다, 다만 1,2,3등 자리가 없을시엔 4등)
				isWin = true;
			}
			
			if(isWin) {
				if(i>=0 && i<avg_first) {
					// 1등
					
					// 오늘 1등 자리가 꽉찼다면
					if(today_first>=avg_first) {
						// 2등으로
						i = avg_first+1;
					}else {
						rank = 1;
						break;
					}
					
					
				}else if(i>=avg_first && i<avg_second) {
					// 2등
					
					// 2등자리 꽉찼다면
					if(today_second>=avg_second) {
						// 3등으로
						i = avg_second+1;
					}else {
						rank = 2;
						break;
					}
					
				}else if(i>=avg_second && i<avg_third) {
					// 3등
					if(today_third>=avg_third) {
						// 4등으로
						break;
					}else {
						rank = 3;
						break;
					}
				}
			}
			
		}
		
		// 당첨자 entity 생성
		Winner winner = Winner.builder()
											.rank(rank)
											.entry(entry)
											.build();
		winnerRepository.save(winner);

		// 오늘의 당첨자 저장
		switch(rank) {
			case 1 : todayWinner = todayWinner.toBuilder().first_place(todayWinner.getFirst_place()+1).build(); break;
			case 2 : todayWinner = todayWinner.toBuilder().second_place(todayWinner.getSecond_place()+1).build(); break;
			case 3 : todayWinner = todayWinner.toBuilder().third_place(todayWinner.getThird_place()+1).build(); break;
		}
		todayWinnerRepository.save(todayWinner);
		
		// 아래 코드는 하루가 지나고 그날의 이벤트 종료 -> 다음날 이벤트 시작 시 해야함
//		// 남은 당첨자 수 조정
//		switch(rank) {
//			case 1 : rw = rw.toBuilder().first_place(rw.getFirst_place()-1).build(); break; 
//			case 2 : rw = rw.toBuilder().second_place(rw.getSecond_place()-1).build(); break; 
//			case 3 : rw = rw.toBuilder().third_place(rw.getThird_place()-1).build(); break; 
//			case 4 : rw = rw.toBuilder().fourth_place(rw.getFourth_place()-1).build(); break; 
//		}
//
//		remainingWinnerRepository.save(rw);
		
		return rank;
		
	}
	
	// 당첨자 화면단 렌더링
	public void eventWinnerRender(Model model) {
		
		// 당일 1등 당첨자 수 구해왔으니 나머지 등수도 구해서 화면에 뿌려줘야함
		// 가능하면 등수별 총 당첨자도 가져오면 좋음
		
		String today = Util.getInstance().dateFormat(LocalDate.now());
		
		// 당일 등수별 당첨자 구하기
		List<Member> daily_first_member_list = memberRepository.findAllByEntryDateAndRank(today, 1);
		List<Member> daily_second_member_list = memberRepository.findAllByEntryDateAndRank(today, 2);
		List<Member> daily_third_member_list = memberRepository.findAllByEntryDateAndRank(today, 3);
		List<Member> daily_fourth_member_list = memberRepository.findAllByEntryDateAndRank(today, 4);

		model.addAttribute("daily_first_member_list", daily_first_member_list);
		model.addAttribute("daily_second_member_list", daily_second_member_list);
		model.addAttribute("daily_third_member_list", daily_third_member_list);
		model.addAttribute("daily_fourth_member_list", daily_fourth_member_list);

		// 총 등수별 당첨자 구하기
		List<Member> total_first_member_list = memberRepository.findAllByRank(1);
		List<Member> total_second_member_list = memberRepository.findAllByRank(2);
		List<Member> total_third_member_list = memberRepository.findAllByRank(3);
		List<Member> total_fourth_member_list = memberRepository.findAllByRank(4);
		
		model.addAttribute("total_first_member_list", total_first_member_list);
		model.addAttribute("total_second_member_list", total_second_member_list);
		model.addAttribute("total_third_member_list", total_third_member_list);
		model.addAttribute("total_fourth_member_list", total_fourth_member_list);
		
	}
	
	
	
//	// 당일 이벤트 종료 메서드
//	// 여기선 관리자가 직접 종료시키지만 실제 구현 시 특정 시간 (ex : 밤10시)에 종료시키도록 구현하면 될 것 같다
//	// 당일 이벤트 응모종료에 따라 응모자 수를 파악하고 당첨자를 결정해야 하며
//	// 등수별 남은 총 당첨자 테이블을 최신화 해줘야 한다
//	public void endEventOfTheDay() {
//		
//		// 당첨자를 결정하기 전, 등수 별 하루 평균 당첨자를 먼저 구해야 한다
//		// 하루 평균 당첨자 = 남은 당첨자 / 남은 기간
//		
//		// 남은 당첨자 entity
//		RemainingWinner rw = remainingWinnerRepository.findById((long) 1).get(); // 현 프로젝트에선 이벤트가 하나뿐이니 id=1 많아지면 event_id로 찾아야함
//		
//		String end_date = eventRepository.findById((long)1).get().getEnd_date();
//		String today = Util.getInstance().dateFormat(LocalDate.now());
//		
//		// 남은 기간
//		int rd = EventUtil.getInstance().getRemainingDuration(today, end_date);
//		
//		if(rd<1) {rd=1;} // 남은기간이 음수이면 안되기 때문에
//		
//		// 등수별 당일 평균 당첨자
//		int avg_first = EventUtil.getInstance().getDailyEverage(rw.getFirst_place(), rd);
//		int avg_second = EventUtil.getInstance().getDailyEverage(rw.getSecond_place(), rd);
//		int avg_third = EventUtil.getInstance().getDailyEverage(rw.getThird_place(), rd);
//		
//		// 1, 2, 3등의 당일 평균 당첨자 만큼 당첨자를 선별하고, 나머지 응모자는 4등처리
//		// 추첨은 당일 총 응모자수를 구한 후, 당첨자를 선별한다
//		
//		// 당일 총 응모자 수
//		int daily_total_entry = (int) entryRepository.countByEntryDateAndEventId(today, (long) 1);
//		
//		// 당일 총 응모자 수에 따른 당첨자 배열 받기
//		int[] daily_total_winner = EventUtil.getInstance().getDailyTotalWinner(daily_total_entry);
//		
//		ArrayList<Entry> entry_list = (ArrayList<Entry>) entryRepository.findAllByEntryDateAndEventId(today, (long)1);
//		
//		// 당첨자들이 들어있는 배열에서 1, 2, 3등 분배 후 나머지 4등 처리 (당첨자는 Winner 테이블에 입력됨)
//		for(int i=0; i<daily_total_winner.length; i++) {
//			
//			if(i>=0 && i<avg_first) {
//				// 1등
//				
//				// 1등 당첨된 entry (-1하는 이유는 get이 0부터 가져오기 때문)
//				Entry first_place_entry = entry_list.get(daily_total_winner[i]-1);
//				
//				// winner 테이블에 입력할 winner build
//				Winner winner_first = Winner.builder()
//															.rank(1)
//															.entry(first_place_entry)
//															.build();
//				// winner 테이블에 입력
//				winnerRepository.save(winner_first);
//				
//			}else if(i>=avg_first && i<avg_second) {
//				// 2등
//				Entry second_place_entry = entry_list.get(daily_total_winner[i]-1);
//				Winner winner_second = Winner.builder()
//						.rank(2)
//						.entry(second_place_entry)
//						.build();
//				winnerRepository.save(winner_second);
//				
//			}else if(i>=avg_second && i<avg_third) {
//				// 3등
//				Entry third_place_entry = entry_list.get(daily_total_winner[i]-1);
//				Winner winner_third = Winner.builder()
//						.rank(3)
//						.entry(third_place_entry)
//						.build();
//				winnerRepository.save(winner_third);
//			}else {
//				// 4등
//				Entry fourth_place_entry = entry_list.get(daily_total_winner[i]-1);
//				Winner winner_fourth = Winner.builder()
//						.rank(4)
//						.entry(fourth_place_entry)
//						.build();
//				winnerRepository.save(winner_fourth);
//				
//			}
//		}
//		
//		// 당일 이벤트 종료시 RemainingWinner (등수별 총 남은 당첨자 수) 값 최신화 
//		// 총 남은 당첨자 수 - 오늘의 당첨자 수
//		int remaining_first = rw.getFirst_place() - winnerRepository.countByRankAndEntryDate(1, today);
//		int remaining_second = rw.getSecond_place() - winnerRepository.countByRankAndEntryDate(2, today);
//		int remaining_third = rw.getThird_place() - winnerRepository.countByRankAndEntryDate(3, today);
//		int remaining_fourth = rw.getFourth_place() - winnerRepository.countByRankAndEntryDate(4, today);
//		
//		rw = rw.toBuilder()
//					.first_place(remaining_first)
//					.second_place(remaining_second)
//					.third_place(remaining_third)
//					.fourth_place(remaining_fourth)
//					.build();
//		
//		remainingWinnerRepository.save(rw);
//	}
	
		
	
}
