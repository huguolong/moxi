package com.moxi.service.impl;

import java.util.Date;
import com.moxi.domain.AppInfo;
import com.moxi.mapper.ApplicationMapper;
import com.moxi.task.ReportToTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moxi.domain.ActivationRecord;
import com.moxi.domain.ClickRecord;
import com.moxi.mapper.ActivationRecordMapper;
import com.moxi.mapper.ClickRecordMapper;
import com.moxi.model.BaseDataResp;
import com.moxi.model.ButtReq;
import com.moxi.service.IApiService;
import com.moxi.common.config.Constant;


import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

@Service
public class ApiServiceImpl implements IApiService {

	@Autowired
	private ReportToTask reportToTask;

	@Resource
	private ClickRecordMapper clickRecordMapper;
	@Resource
	private ActivationRecordMapper activationRecordMapper;
	@Resource

	private ApplicationMapper applicationMapper;

	@Override
	public BaseDataResp clickNotice(ButtReq req, HttpServletRequest request) {
		BaseDataResp resp = new BaseDataResp();
		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(req.getAppid());
		//保存点击记录
		appInfo = applicationMapper.findByAppId(appInfo);
		if(null == appInfo){
			resp.setCode(Constant.Commons.ERROR_DATA_NOT);
			resp.setDescription("data does not exist");
			return resp;
		}
		ClickRecord clickRecord = new ClickRecord();
		clickRecord.setReqUrl(req.getReqUrl());
		clickRecord.setReqParam(req.getReqParam());
		clickRecord.setAppId(req.getAppid());
		clickRecord.setChannelId(appInfo.getChannelId());
		clickRecord.setIdfa(req.getIdfa());
		clickRecord.setUa(req.getUa());
		clickRecord.setIp(req.getIp());
		clickRecord.setCallbackAddress(req.getCallback());
		clickRecord.setIsActivation(Integer.valueOf(Constant.Commons.ZERO));
		clickRecord.setCreateTime(new Date());
		clickRecordMapper.insert(clickRecord);
		final Integer clickId = clickRecord.getId();
		final ButtReq buttReq = req;

		reportToTask.toAppTask(clickId,appInfo,req);

		resp.setCode(Constant.Commons.SUCCESS_CODE);
		return resp;
	}

	@Override
	public BaseDataResp activationNotice(ButtReq req) {
		
		BaseDataResp resp = new BaseDataResp();
		//接到应用方用户已激活通知后，保存激活记录并修改 点击记录为激活
		Integer clickId = Integer.parseInt(req.getClick_id());
		ClickRecord clickRecord = clickRecordMapper.findById(clickId);
		if(null == clickRecord){
			resp.setCode(Constant.Commons.ERROR_DATA_NOT);
			resp.setDescription("data does not exist");
			return resp;
		}
		ActivationRecord info = activationRecordMapper.findByClickIdOrIdfa(clickId);
		if(info != null) {
			resp.setCode(Constant.Commons.ERROR_ACTIVED);
			resp.setDescription("this idfa has been actived");
			return resp;
		}

		//添加激活记录
		ActivationRecord activationRecord = new ActivationRecord();
		activationRecord.setClickId(clickId);
		activationRecord.setReqUrl(req.getReqUrl());
		activationRecord.setReqParam(req.getReqParam());
		activationRecord.setIsNotice(Integer.valueOf(Constant.Commons.ZERO));
		activationRecord.setCreateTime(new Date());
		activationRecordMapper.insert(activationRecord);

		//更新点击记录为已激活
		clickRecord.setIsActivation(Integer.valueOf(Constant.Commons.ONE));
		clickRecordMapper.updateByIsActivation(clickRecord);

		//应用方通知用户已激活后-通知用户平台
		reportToTask.toUserPlatform(clickId,activationRecord.getId());

		resp.setCode(Constant.Commons.SUCCESS_CODE);
		return resp;
	}


	
}
