package com.moxi.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class BaseResp implements Serializable{
	
	private static final long serialVersionUID = -2785244997177694228L;
	/**
	 * 状态码
	 */
	private String code;
	/**
	 * 描述
	 */
	private String description;
	
	public BaseResp() {
		code = "0001";
		description = "ERROR_UNKNOWN";
	}

	public void setCode(String code) {
		this.code = code;
		if ("0000".equals(code) || "200".equals(code))
			setDescription("SUCCESS");
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
