package com.wisekit.testglobal.util;

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
	
	// 일별 응모자 수 파악 후 그날의 당첨 확률을 리턴하는 함수
	
}
