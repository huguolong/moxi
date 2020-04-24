package com.moxi.task;

import com.moxi.domain.AppInfo;
import com.moxi.domain.ClickRecord;
import com.moxi.mapper.ActivationRecordMapper;
import com.moxi.mapper.ApplicationMapper;
import com.moxi.mapper.ClickRecordMapper;
import com.moxi.common.config.Constant;
import com.moxi.util.CommonUtil;
import com.moxi.util.HttpClientUtils;
import com.moxi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author hgl
 */
@Component
@Configuration
@EnableScheduling
public class ReportToTask {

    private final static Logger logger = LoggerFactory.getLogger(ReportToTask.class);

    @Resource
    private ClickRecordMapper clickRecordMapper;
    @Resource
    private ActivationRecordMapper activationRecordMapper;
    @Resource
    private ApplicationMapper applicationMapper;


    @Scheduled(cron = "0 0 */2 * * ?")
    public void toAppTask(){
        try {
            HashMap<String,Object> param = new HashMap<>();
            param.put("startTime",CommonUtil.getStartTime());
            param.put("endTime",CommonUtil.getEndTime());
            List<ClickRecord> list = clickRecordMapper.listByTime(param);
            logger.info("定时更新无上报结果的点击记录任务-记录数为->:{}",list.size());
            for (ClickRecord cr:list){
                System.out.println(cr.getAppId());

                AppInfo appInfo = new AppInfo();

                appInfo.setAppId(cr.getAppId());
                //保存点击记录
                appInfo = applicationMapper.findByAppId(appInfo);

                String callback = URLEncoder.encode(String.format(Constant.ReportedUrl.CALL_BACK,cr.getId(),cr.getIdfa()),"UTF-8");
                String url = appInfo.getReqUrl()+appInfo.getReqParam();
                if(StringUtils.isEmpty(cr.getUa())){
                    cr.setUa("ua");
                }
                url = String.format(url,cr.getIdfa(),URLEncoder.encode(cr.getUa(),"UTF-8"),cr.getIp(),callback);
                logger.info("上报应用方激活URL:{}",url);
                String result = HttpClientUtils.sendHttpsGet(url, null);
                logger.info("返回结果:{}",result);
                ClickRecord clickRecord = new ClickRecord();
                clickRecord.setId(cr.getId());
                clickRecord.setResult(result);
                clickRecordMapper.updateByResult(clickRecord);

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("任务异常-定时更新无上报结果的点击记录任务....");
        }
    }
}
