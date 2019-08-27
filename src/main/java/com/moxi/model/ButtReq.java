package com.moxi.model;

import java.io.Serializable;

/**
 * 对接入参数
 * @author Administrator
 *
 */
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

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getClick_id() {
		return click_id;
	}

	public void setClick_id(String click_id) {
		this.click_id = click_id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public String getReqParam() {
		return reqParam;
	}

	public void setReqParam(String reqParam) {
		this.reqParam = reqParam;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "ButtReq [appid=" + appid + ", idfa=" + idfa + ", click_id=" + click_id + ", channel=" + channel
				+ ", sign=" + sign + ", callback=" + callback + ", reqUrl=" + reqUrl + ", reqParam=" + reqParam + ", ua=" + ua + ", ip=" + ip + "]";
	}
}
