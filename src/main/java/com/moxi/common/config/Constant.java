package com.moxi.common.config;

public interface Constant {
	
	String Code01 = "01";
	String Msg01 = "请求成功"; 
	String OrderByAddDateDesc = "ADDDATE DESC";
	String OrderByAddDateAsc = "ADDDATE ASC";
	String OrderByLikesDesc = "LIKES DESC";
	String OrderByBrowsesDesc = "BROWSES DESC";
	String OrderByCommentsDesc = "COMMENTS DESC";
	String OrderByScoreDesc = "SCORE DESC";
	
	public static interface Commons {
		public static final String HTTP_OK = "200";
		public static final String SUCCESS_CODE = "0000";		//成功
		public static final String ERROR_UNKNOWN = "0001"; 	//系统错误
		public static final String ERROR_PARAMETER = "0003";	//参数错误
		public static final String ERROR_ACTIVED = "0004"; 	//已激活
		public static final String ERROR_DATA_NOT = "0005";	//数据不存在
		public static final String ERROR_DATA_IN = "0006";	//数据已存在
		
		public static final String SUCCESS_DESC = "SUCCESS";
		
		public static final String MINUS_ONE = "-1";
		public static final String ZERO = "0";
		public static final String ONE = "1";
		public static final String TWO = "2";
		public static final String THREE = "3";
		public static final String FOUR = "4";
		public static final String FIVE = "5";
		public static final String SIX = "6";
		public static final String SEVEN = "7";
		public static final String EIGHT = "8";
		public static final String NINE = "9";
	}
	
	/**
	 * 推广方上报接口
	 *
	 */
	public interface ReportedUrl{
		
		/**
		 * 饿了么渠道推广方
		 */
		public static String ELEME_CHANNEL_URL = "https://channelcube.ele.me/click/idfa";

		public static String CALL_BACK = "http://api.adlianyue.com/api/jhNotice?click_id=%s&idfa=%s";
		
	}
}
