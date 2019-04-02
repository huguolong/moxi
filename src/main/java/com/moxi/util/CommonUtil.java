package com.moxi.util;

import java.text.NumberFormat;

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
		nf.setMinimumFractionDigits(2);//控制保留小数点后几位，2：表示保留2位小数点
		percent = nf.format(p3);
		return percent;
	}
	
}
