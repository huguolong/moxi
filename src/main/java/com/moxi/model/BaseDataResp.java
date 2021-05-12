package com.moxi.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class BaseDataResp extends BaseResp implements Serializable{
	
	private static final long serialVersionUID = -4774869782747473448L;
	
	/**
	 * 数据
	 */
	private Object data;
	
	public BaseDataResp() {
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
