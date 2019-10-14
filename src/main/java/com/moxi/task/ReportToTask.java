package com.moxi.task;

import com.moxi.cache.AppRecallCache;
import com.moxi.domain.ActivationRecord;
import com.moxi.domain.AppInfo;
import com.moxi.domain.ClickRecord;
import com.moxi.mapper.ActivationRecordMapper;
import com.moxi.mapper.ClickRecordMapper;
import com.moxi.model.ButtReq;
import com.moxi.common.config.Constant;
import com.moxi.util.HttpClientUtils;
import com.moxi.util.RecallUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.net.URLEncoder;

@Component
public class ReportToTask {

    private final static Logger logger = LoggerFactory.getLogger(ReportToTask.class);

    @Resource
    private ClickRecordMapper clickRecordMapper;
    @Resource
    private ActivationRecordMapper activationRecordMapper;
    @Autowired
    private AppRecallCache appRecallCache;


    @Async("taskExecutor")
    public void toAppTask(Integer clickId, AppInfo appInfo, ButtReq buttReq){

//        try {
//            String callback = URLEncoder.encode(String.format(Constant.ReportedUrl.CALL_BACK,clickId,buttReq.getIdfa()),"UTF-8");
//            String url = appInfo.getReqUrl()+appInfo.getReqParam();
//            url = String.format(url,buttReq.getIdfa(),URLEncoder.encode(buttReq.getUa(),"UTF-8"),buttReq.getIp(),callback);
//            logger.info("上报应用方激活URL:{}",url);
//            String result = HttpClientUtils.sendHttpsGet(url, null);
//            logger.info("返回结果:{}",result);
//            ClickRecord cr = new ClickRecord();
//            cr.setId(clickId);
//            cr.setResult(result);
//            clickRecordMapper.updateByResult(cr);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Async("taskExecutor2")
    public void toUserPlatform(Integer clickId,Integer activionId){
//        try {
//            ClickRecord clickRecord = clickRecordMapper.findById(clickId);
//
//            //判断该应用回调率
//            boolean isCall = RecallUtil.isCallback(appRecallCache.getAppRecall(clickRecord.getAppId()));
//            if(isCall){
//                if(!StringUtils.isEmpty(clickRecord.getCallbackAddress())){
//                    logger.info("通知用户平台-用户已激活callback:{}",clickRecord.getCallbackAddress());
//                    String result = HttpClientUtils.sendHttpsGet(clickRecord.getCallbackAddress(), null);
//                    logger.info("通知用户平台-用户已激活返回结果:{}",result);
//                    ActivationRecord activation = new ActivationRecord();
//                    activation.setId(activionId);
//                    activation.setIsNotice(Integer.valueOf(Constant.Commons.ONE));
//                    activation.setResult(result);
//                    activationRecordMapper.updateByResult(activation);
//                }else{
//                    logger.info("通知用户平台-回调地址为空！clickId:{},activionId:{}",clickId,activionId);
//                }
//            }else{
//                ActivationRecord activation = new ActivationRecord();
//                activation.setId(activionId);
//                activation.setIsNotice(Integer.valueOf(Constant.Commons.TWO));
//                activationRecordMapper.updateByResult(activation);
//
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
