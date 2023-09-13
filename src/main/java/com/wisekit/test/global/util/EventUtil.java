package com.wisekit.test.global.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EventUtil {
	
	private static EventUtil eventUtil;
	
	private EventUtil() {}
	
	public static EventUtil getInstance() {
		if(eventUtil==null) {
			return new EventUtil();
		}
		return eventUtil;
	}
	
	// 등수 별 하루 평균 당첨자 리턴하는 함수
	public int getDailyEverage(int remaining_winner, int remaining_duration){
		
		System.out.println("remaining_winner : "+remaining_winner);
		System.out.println("remaining_duration : "+remaining_duration);
		
		// 하루 평균 당첨자 = 남은 당첨자 / 남은 기간
		int daily_everage =  (int) Math.floor((remaining_winner / remaining_duration));
		
		System.out.println("daily_everage : "+daily_everage);
		
		return daily_everage;
	}
	
	// 남은기간 구하기
	public int getRemainingDuration(String today, String end_date) {
		
		LocalDate cur = LocalDate.parse(today);
		LocalDate end = LocalDate.parse(end_date);
		
		int remaining_duration = ((int)ChronoUnit.DAYS.between(cur, end))+1;
		System.out.println("남은 기간 : "+remaining_duration);
		
		return remaining_duration; // +1 하는 이유 : 마지막날도 이벤트 하기 때문에
	}
	
	// 당일 총 응모자수에 따른 당첨자가 순서대로 담긴 배열 반환하는 함수
	public int[] getDailyTotalWinner(int daily_total_entry) {
		
		// solution
		// 당일 총 응모자수 크기의 배열을 만들고, 1~총 응모자수의 수를 랜덤하게 입력 (중복값 처리해야함)
		// 랜덤하게 들어간 값의 순서대로 1 ~ 1등 평균수 : 1등 / 1등 평균 수 ~ 2등 평균 수 : 2등 / 2등 평균 수 ~ 3등 평균 수 : 3등
		// (이미 당일 등수별 당첨자 평균을 구하면서 이벤트 종료까지 1, 2, 3등이 모두 당첨 될 수 있게 처리해놨기 때문에 여기선 당첨자만 선별하면 된다.)
		
		int[] daily_total_winner = new int[daily_total_entry];
		
		// 배열에 당첨자(중복 제거한 랜덤값) 넣기
		for(int i=0; i<daily_total_winner.length; i++) {
			
			// 1~총 응모자수 사이의 랜덤한 값
			int random_num = (int) (Math.random()*daily_total_entry) + 1;
			daily_total_winner[i] = random_num;

			// 중복 제거
			for(int j=0; j<i; j++) {
				if(daily_total_winner[i]==daily_total_winner[j]) {
					i--;
					break;
				}
			}
		}
		
		return daily_total_winner;
	}
	
}
