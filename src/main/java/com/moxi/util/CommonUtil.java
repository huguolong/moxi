package com.moxi.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

	public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public final static String DEFAULT_MONTH_FORMAT = "yyyy-MM";


	public static String getPercent(Long num,Long totalPeople ){
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
		return todayStart.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
	}

	/**
	 * 获取每天的开始时间 23:59:59:999
	 * @return
	 */
	public static String getEndTime() {
		LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		return todayEnd.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
	}

	/**
	 * 获取当前月份
	 */
	public static String getCurrentMonth(){
		return DateTimeFormatter.ofPattern(DEFAULT_MONTH_FORMAT).format(LocalDateTime.now());
	}

	/**
	 * 获取日期月份
	 */
	public static String getDateMonth(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
		return DateTimeFormatter.ofPattern(DEFAULT_MONTH_FORMAT).format(LocalDate.parse(date, formatter));
	}

	/**
	 * 获取前一天日期
	 */
	public static String getBeforeDay(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		Date d=cal.getTime();
		SimpleDateFormat sp=new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		return sp.format(d);
	}
}
