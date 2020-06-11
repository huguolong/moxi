package com.moxi.task;

import com.moxi.domain.AppInfo;
import com.moxi.domain.ClickRecord;
import com.moxi.mapper.ApplicationMapper;
import com.moxi.mapper.ClickRecordMapper;
import com.moxi.common.config.Constant;
import com.moxi.service.IApplicationService;
import com.moxi.util.CommonUtil;
import com.moxi.util.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${task.open}")
    private boolean taskOpen;
    @Resource
    private ClickRecordMapper clickRecordMapper;
    @Resource
    private ApplicationMapper applicationMapper;
    @Resource
    private IApplicationService applicationService;

    @Scheduled(cron = "0 0 */2 * * ?")
    public void toAppTask(){

        if(taskOpen){
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

    /**
     * 每天凌晨2点触发
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void statisticsToTask1(){
        if(taskOpen){
            //统计应用所有的数据
            applicationService.pressAllStatisticsApp();
        }
    }

    /**
     * 每天凌晨2点触发
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void statisticsToTask2(){
        if(taskOpen){
            //获取前一天日期
            String date = CommonUtil.getBeforeDay();
            applicationService.pressDayStatisticsApp(date);
        }

    }





}
