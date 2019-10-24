package com.moxi.model;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
import java.io.Serializable;

/**
 * 对接入参数
 * @author Administrator
 *
 */
@Data
public class ButtReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String appid;
	
	private String idfa;
	
	private String click_id;
	
	private String channel;
	
	private String sign;
	
	private String callback;
	
	private String reqUrl;
	
	private String reqParam;

	/**
	 * 设备浏览器中的 ua 信息，需用 urlencode 编
	 */
	private String ua;
	/**
	 * 点击广告的设备 IP 信息
	 */
	private String ip;

	private String cCode;
}
