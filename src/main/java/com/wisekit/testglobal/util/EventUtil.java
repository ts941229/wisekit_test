package com.wisekit.testglobal.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
	
	// 일별 응모자 수 파악 후 그날의 당첨 확률을 리턴하는 함수
	
	
	// 날짜 포맷 (ex : 2023-09-12)
	public String dateFormat(LocalDate date) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		return date.format(formatter);
	}
	
	// 남은기간 구하기
	public int getRemainingDuration(String today, String end_date) {
		
		LocalDate cur = LocalDate.parse(today);
		LocalDate end = LocalDate.parse(end_date);
		
		int remaining_duration = ((int)ChronoUnit.DAYS.between(cur, end))+1;
		System.out.println("남은 기간 : "+remaining_duration);
		
		return remaining_duration; // +1 하는 이유 : 마지막날도 이벤트 하기 때문에
	}
	
	// 당일 총 응모자수에 따른 확률 조정
	public void chanceByEntryCount(int daily_total_entry) {
		
		
		Math.random();
	}
	
}
