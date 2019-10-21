package com.moxi.util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
	
	public static String getPercent(Integer num,Long totalPeople ){
		String percent ;
		Double p3 = 0.0;
		if(totalPeople == 0){
		p3 = 0.0;
		}else{
		p3 = num*1.0/totalPeople;
		}
		NumberFormat nf = NumberFormat.getPercentInstance();
		//控制保留小数点后几位，2：表示保留2位小数点
		nf.setMinimumFractionDigits(2);
		percent = nf.format(p3);
		return percent;
	}

	/**
	 * 获取每天的开始时间 00:00:00:00
	 * @return
	 */
	public static String getStartTime() {
		LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		return todayStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 获取每天的开始时间 23:59:59:999
	 * @return
	 */
	public static String getEndTime() {
		LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		return todayEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
}
