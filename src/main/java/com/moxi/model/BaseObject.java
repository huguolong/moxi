package com.moxi.model;

import lombok.Data;

@Data
public class BaseObject {

	private int start;
	private int end;
	private int pageCurrent;
	private int pageSize;
	private int pageCount;
	private String orderBy;
	private String searchKey;

}
