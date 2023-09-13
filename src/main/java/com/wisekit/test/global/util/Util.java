package com.wisekit.test.global.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {
	
	private static Util util;
	
	private Util() {}
	
	public static Util getInstance() {
		if(util==null) {
			util = new Util();
		}
		return util;
	}
	
	// 날짜 포맷 (ex : 2023-09-12)
	public String dateFormat(LocalDate date) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		return date.format(formatter);
	}
	
}
