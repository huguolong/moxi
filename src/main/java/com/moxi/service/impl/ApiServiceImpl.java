package com.moxi.service.impl;

import java.net.URLEncoder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.moxi.cache.AppRecallCache;
import com.moxi.domain.AppChannel;
import com.moxi.domain.AppInfo;
import com.moxi.mapper.AppChannelMapper;
import com.moxi.mapper.ApplicationMapper;
import com.moxi.model.RabbitmqMessageVo;
import com.moxi.mq.RabbitmqSender;
import com.moxi.task.ReportToTask;
import com.moxi.util.HttpClientUtils;
import com.moxi.util.RecallUtil;
import com.moxi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.moxi.domain.ActivationRecord;
import com.moxi.domain.ClickRecord;
import com.moxi.mapper.ActivationRecordMapper;
import com.moxi.mapper.ClickRecordMapper;
import com.moxi.model.BaseDataResp;
import com.moxi.model.ButtReq;
import com.moxi.service.IApiService;
import com.moxi.common.config.Constant;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class ApiServiceImpl implements IApiService {

	private final static Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

	@Resource
	private AppRecallCache appRecallCache;
	@Resource
	private RabbitmqSender rabbitmqSender;

	@Resource
	private ClickRecordMapper clickRecordMapper;
	@Resource
	private ActivationRecordMapper activationRecordMapper;
	@Resource
	private ApplicationMapper applicationMapper;
	@Resource
	private AppChannelMapper appChannelMapper;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public BaseDataResp clickNotice(ButtReq req, HttpServletRequest request) {

		logger.info("点击请求>>> appId:{} >> channelCode:{} >> idfa:{} ", req.getAppid(),req.getCCode(),req.getIdfa());
		BaseDataResp resp = new BaseDataResp();

		//查看该渠道对接应用是否启用
		if(!StringUtil.isNull(req.getCCode())){
			Map<String,String> param = new HashMap<>();
			param.put("channelCode",req.getCCode());
			param.put("appId",req.getAppid());
			AppChannel appChannel = appChannelMapper.findByCodeAndApp(param);
			if(null != appChannel){
				if(appChannel.getStatus() != 1){
					resp.setCode(Constant.Commons.ERROR_PARAMETER);
					resp.setDescription("该应用已停止服务！");
					return resp;
				}
			}
		}

		AppInfo appInfo = new AppInfo();
		appInfo.setAppId(req.getAppid());
		//保存点击记录
		appInfo = applicationMapper.findByAppId(appInfo);
		if(null == appInfo){
			resp.setCode(Constant.Commons.ERROR_DATA_NOT);
			resp.setDescription("该应用不存在！");
			return resp;
		}

		if(appInfo.getStatus() != 1){
			resp.setCode(Constant.Commons.ERROR_PARAMETER);
			resp.setDescription("该应用已停止服务！");
			return resp;
		}

		//加入消息队列
		RabbitmqMessageVo rabbitmqMessageVo = RabbitmqMessageVo.custom().build();
		rabbitmqMessageVo.setQueue(rabbitmqSender.getQueueCenter());
		rabbitmqMessageVo.setDirectExchange(rabbitmqSender.getExchangeName());
		rabbitmqMessageVo.setRoutingKey(rabbitmqSender.getQueueCenterKey());
		rabbitmqMessageVo.setTags(req.getAppid()+"|"+req.getCCode()+"|"+req.getIdfa());
		rabbitmqMessageVo.setData(req);
		rabbitmqSender.sendNotifyMessage(rabbitmqMessageVo);
//		//查询是否重复点击
//		int num = clickRecordMapper.countByIdfa(req.getAppid(),req.getIdfa());
//		if(num >= 1){
//			resp.setCode(Constant.Commons.ERROR_DATA_IN);
//			resp.setDescription("数据已存在-重复点击！");
//			return resp;
//		}

//		toAppTask(clickId, appInfo, req);

		resp.setCode(Constant.Commons.SUCCESS_CODE);
		return resp;
	}

	@Override
	public BaseDataResp makeUpNoticeApp(String clickIds) throws Exception{
		logger.info("手动激活通知请求：{}", clickIds);
		String[] ids = clickIds.split(",");
		BaseDataResp resp = new BaseDataResp();
		if(ids.length>0){
			for (String clickId:ids ){
				ClickRecord clickRecord = clickRecordMapper.findById(Integer.valueOf(clickId));
				if(clickRecord == null){
					clickRecord = clickRecordMapper.findByIdBack(Integer.valueOf(clickId));
				}
				AppInfo appInfo = new AppInfo();
				appInfo.setAppId(clickRecord.getAppId());
				//保存点击记录
				appInfo = applicationMapper.findByAppId(appInfo);
				String callback = URLEncoder.encode(String.format(Constant.ReportedUrl.CALL_BACK,clickId,clickRecord.getIdfa()),"UTF-8");
				String url = appInfo.getReqUrl()+appInfo.getReqParam();
				url = String.format(url,clickRecord.getIdfa(),URLEncoder.encode(clickRecord.getUa(),"UTF-8"),clickRecord.getIp(),callback);
				logger.info("上报应用方激活URL:{}",url);
				String result = HttpClientUtils.sendHttpsGet(url, null);
				logger.info("返回结果:{}",result);
				ClickRecord cr = new ClickRecord();
				cr.setId(Integer.valueOf(clickId));
				cr.setResult(result);
				clickRecordMapper.updateByResult(cr);
			}
		}
		resp.setCode(Constant.Commons.SUCCESS_CODE);
		return resp;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public BaseDataResp activationNotice(ButtReq req) {

		logger.info("用户激活推广方通知请求：{}", JSONObject.toJSON(req));

		BaseDataResp resp = new BaseDataResp();
		//接到应用方用户已激活通知后，保存激活记录并修改 点击记录为激活
		Integer clickId = Integer.parseInt(req.getClick_id());
		ClickRecord clickRecord = clickRecordMapper.findById(clickId);
		if(clickRecord == null){
			clickRecord = clickRecordMapper.findByIdBack(clickId);
		}
		if(null == clickRecord){
			resp.setCode(Constant.Commons.ERROR_DATA_NOT);
			resp.setDescription("data does not exist");
			logger.error("用户激活推广方通知请求-数据库数据不存在：{}",JSONObject.toJSON(req));

			//添加激活记录
			ActivationRecord activationRecord = new ActivationRecord();
			activationRecord.setClickId(clickId);
			activationRecord.setReqUrl(req.getReqUrl());
			activationRecord.setReqParam(req.getReqParam());
			activationRecord.setIdfa(req.getIdfa());
			activationRecord.setIsNotice(Integer.valueOf(Constant.Commons.ZERO));
			activationRecord.setCreateTime(new Date());
			activationRecord.setResult("数据库不存在-点击上报");
			activationRecordMapper.insert(activationRecord);

			return resp;
		}
		ActivationRecord info = activationRecordMapper.findByClickIdOrIdfa(clickId);
		if(info != null) {
			resp.setCode(Constant.Commons.ERROR_ACTIVED);
			resp.setDescription("this idfa has been actived");
			logger.error("用户激活推广方通知请求-idfa为数据库激活状态：{}",JSONObject.toJSON(req));
			return resp;
		}

		//添加激活记录
		ActivationRecord activationRecord = new ActivationRecord();
		activationRecord.setClickId(clickId);
		activationRecord.setReqUrl(req.getReqUrl());
		activationRecord.setReqParam(req.getReqParam());
		activationRecord.setIsNotice(Integer.valueOf(Constant.Commons.ZERO));
		activationRecord.setIdfa(req.getIdfa());
		activationRecord.setCreateTime(new Date());
		activationRecordMapper.insert(activationRecord);

		//更新点击记录为已激活
		clickRecord.setIsActivation(Integer.valueOf(Constant.Commons.ONE));
		clickRecordMapper.updateByIsActivation(clickRecord);
		clickRecordMapper.updateByIsActivation1(clickRecord);
		//应用方通知用户已激活后-通知用户平台
		toUserPlatform(clickId,activationRecord.getId());

		resp.setCode(Constant.Commons.SUCCESS_CODE);
		return resp;
	}

	@Override
	@Async("taskExecutor")
	public void toAppTask(Integer clickId, AppInfo appInfo, ButtReq buttReq){
		try {
			String callback = URLEncoder.encode(String.format(Constant.ReportedUrl.CALL_BACK,clickId,buttReq.getIdfa()),"UTF-8");
			String url = appInfo.getReqUrl()+appInfo.getReqParam();
			if(StringUtils.isEmpty(buttReq.getUa())){
				buttReq.setUa("ua");
			}
			logger.info("上报应用方激活 >> idfa:{} >> url:{} ",buttReq.getIdfa(),url);
			if(!StringUtil.isNull(buttReq.getTs())){
				url = String.format(url,buttReq.getIdfa(),URLEncoder.encode(buttReq.getUa(),"UTF-8"),buttReq.getIp(),callback,System.currentTimeMillis());
			}else{
				url = String.format(url,buttReq.getIdfa(),URLEncoder.encode(buttReq.getUa(),"UTF-8"),buttReq.getIp(),callback);
			}

			Long timeConsuming1 = Duration.between(buttReq.getBeginTime(),LocalDateTime.now()).toMillis();
			String result = HttpClientUtils.sendHttpsGet(url, null);
			if(result.length() > 100){
				result = result.substring(0,100);
				logger.info("返回结果:{}",result);
			}
			Long timeConsuming2 = Duration.between(buttReq.getBeginTime(),LocalDateTime.now()).toMillis();
			logger.info("返回结果:{} ,业务处理耗时：{}ms,上报耗时间：{}ms,总耗时：{}ms",result,timeConsuming1,timeConsuming2-timeConsuming1,timeConsuming2);
			ClickRecord cr = new ClickRecord();
			cr.setId(clickId);
			cr.setResult(result);
			clickRecordMapper.updateByResult(cr);

		}catch (Exception e) {
			e.printStackTrace();
			logger.error("通知异常-上报应用方激活：{}",clickId);
		}
	}

	@Async("taskExecutor2")
	public void toUserPlatform(Integer clickId,Integer activionId){
		try {
			ClickRecord clickRecord = clickRecordMapper.findById(clickId);
			if(clickRecord == null){
				clickRecord = clickRecordMapper.findByIdBack(clickId);
			}
			//判断该应用回调率
			boolean isCall = RecallUtil.isCallback(appRecallCache.getAppRecall(clickRecord.getAppId(),clickRecord.getChannelCode()));
			if(isCall){
				if(!StringUtils.isEmpty(clickRecord.getCallbackAddress())){
					logger.info("通知用户平台-用户已激活callback:{}",clickRecord.getCallbackAddress());
					String result = HttpClientUtils.sendHttpsGet(clickRecord.getCallbackAddress(), null);
					logger.info("通知用户平台-用户已激活返回结果:{}",result);
					ActivationRecord activation = new ActivationRecord();
					activation.setId(activionId);
					activation.setIsNotice(Integer.valueOf(Constant.Commons.ONE));
					activation.setResult(result);
					activationRecordMapper.updateByResult(activation);
				}else{
					logger.info("通知用户平台-回调地址为空！clickId:{},activionId:{}",clickId,activionId);
				}
			}else{
				ActivationRecord activation = new ActivationRecord();
				activation.setId(activionId);
				activation.setIsNotice(Integer.valueOf(Constant.Commons.TWO));
				activationRecordMapper.updateByResult(activation);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("通知异常-用户已激活 activionId：{}",activionId);
		}
	}

	
}
