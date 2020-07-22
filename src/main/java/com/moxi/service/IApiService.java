package com.moxi.service;

import com.moxi.domain.AppInfo;
import com.moxi.model.BaseDataResp;
import com.moxi.model.ButtReq;

import javax.servlet.http.HttpServletRequest;

public interface IApiService {
	
	BaseDataResp activationNotice(ButtReq req);
	
	BaseDataResp clickNotice(ButtReq req, HttpServletRequest request);

	BaseDataResp makeUpNoticeApp(String clickIds)throws Exception;

	void toAppTask(Integer clickId, AppInfo appInfo, ButtReq buttReq);
	
}
