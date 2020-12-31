package com.moxi.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.moxi.cache.AppRecallCache;
import com.moxi.task.ReportToTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moxi.model.BaseDataResp;
import com.moxi.model.ButtReq;
import com.moxi.service.IApiService;

@RestController
public class ApiController {

	@Autowired
	private IApiService apiService;

	/**
	 * 用户平台安装通知
	 */
	@GetMapping("/api/clickNotice")
	public BaseDataResp clickNotice(ButtReq req,HttpServletRequest request){

		StringBuffer reqUrl = request.getRequestURL();
		String reqParam = request.getQueryString();
		req.setReqUrl(reqUrl.toString());
		req.setReqParam(reqParam);

		return apiService.clickNotice(req,request);
	}

	/**
	 * 用户激活推广方通知
	 */
	@GetMapping("/api/jhNotice")
	public BaseDataResp activationNotice(ButtReq req,HttpServletRequest request){

		StringBuffer reqUrl = request.getRequestURL();
		String reqParam = request.getQueryString();
		req.setReqUrl(reqUrl.toString());
		req.setIdfa(request.getParameter("idfa"));
		req.setReqParam(reqParam);

		return apiService.activationNotice(req);
	}

	@GetMapping("/api/makeUpNoticeApp")
	public BaseDataResp makeUpNoticeApp(String clickIds)throws Exception{
		return apiService.makeUpNoticeApp(clickIds);
	}

}	
