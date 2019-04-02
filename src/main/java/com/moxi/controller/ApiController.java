package com.moxi.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
		req.setReqParam(reqParam);

		return apiService.activationNotice(req);
	}


		
	public static void main(String[] args) throws Exception{
		clickReportedBjjb();
	}

	public static void clickReportedBjjb()throws Exception{
		String idfa = "3BA5ADEE-6467-555-9424-6D873071777";
		String clickId = "1";
		Map<String, Object> params = new HashMap<>();
		params.put("qd", "ly-jd");
		params.put("s_id","9v04mmn");
		params.put("uuid", idfa);
		params.put("ua", "Mozilla%2f5.0+(iPhone%3b+CPU+iPhone+OS+8_4+like+Mac+OS+X)+AppleWebKit%2f600.1.4+(KHTML%2c+like+Gecko)+Version%2f8.0+Mobile%2f12H155+Safari%2f600.1.4");
		params.put("ip","192.168.0.1");
		params.put("callback",URLEncoder.encode("http://www.adlianyue.com/api/jhNotice?click_id="+clickId+"&idfa="+idfa,"UTF-8"));
//		String result = HttpClientUtils.sendHttpsGet(Constant.ReportedUrl.JB_JD_URL, params);
//		System.out.println(result);
	}

}	
