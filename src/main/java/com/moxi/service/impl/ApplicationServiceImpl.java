package com.moxi.service.impl;

import com.moxi.domain.AppInfo;
import com.moxi.mapper.ActivationRecordMapper;
import com.moxi.mapper.AppStatisticsMapper;
import com.moxi.mapper.ApplicationMapper;
import com.moxi.mapper.ClickRecordMapper;
import com.moxi.service.IApplicationService;
import com.moxi.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);
    @Resource
    private ApplicationMapper applicationMapper;
    @Resource
    private ClickRecordMapper clickRecordService;
    @Resource
    private AppStatisticsMapper appStatisticsMapper;
    @Resource
    private ActivationRecordMapper activationRecordService;

    @Override
    public void pressDayStatisticsApp(String date){

        String month = CommonUtil.getDateMonth(date);
        logger.info("开始统计--{}--点击激活数据",date);
        //查询应用列表
        List<AppInfo> appList = applicationMapper.getAppList();
        for(AppInfo appInfo:appList){
            //渠道详情统计（每天统计前一天的数据）,将当天的数据按月份，日期，渠道保存到数据库中，方便查询。
            Map<String,String> param = new HashMap<>(2);
            param.put("appId",appInfo.getAppId());
            param.put("date",date);

            List<Map<String,Object>> list = clickRecordService.countAppClickInfoByChannel(param);
            for(Map<String,Object> map : list){
                long activationNum = 0;
                if(map.containsKey("activationNum")){
                    activationNum = ((BigDecimal) map.get("activationNum")).longValue();
                }else{
                    map.put("activationNum",activationNum);
                }
                long pcClickNum = (Long)map.get("pcClickNum");
                map.put("conversion",CommonUtil.getPercent(activationNum, pcClickNum));
                long noticeNum = 0L;
                if(map.containsKey("noticeNum")){
                    noticeNum = ((BigDecimal)map.get("noticeNum")).longValue();
                }
                map.put("noticeNum",noticeNum);
                map.put("appId",appInfo.getAppId());
                map.put("month",month);
                appStatisticsMapper.insertChannelStatistics(map);
            }

            //总数详情统计（每天统计前一天的数据），将当天的数据按月份，日期，保存到数据库中，方便查询。
            List<Map<String,Object>> totalList = clickRecordService.countAppClickInfo(param);
            for(Map<String,Object> map : totalList){
                long activationNum = 0;
                if(map.containsKey("activationNum")){
                    activationNum = ((BigDecimal) map.get("activationNum")).longValue();
                }else{
                    map.put("activationNum",activationNum);
                }
                long pcClickNum = (Long)map.get("pcClickNum");
                map.put("conversion", CommonUtil.getPercent(activationNum, pcClickNum));
                if(!map.containsKey("noticeNum")){
                    map.put("noticeNum",0);
                }
                map.put("appId",appInfo.getAppId());
                map.put("month",month);
                appStatisticsMapper.insertTotalStatistics(map);
            }
        }
        logger.info("完成统计》》》{}》》》点击激活数据",date);
    }

    @Override
    public void pressAllStatisticsApp(){

        logger.info("开始统计应用所有的--点击激活数据");

        String startTime = CommonUtil.getStartTime();
        //查询应用列表
        List<AppInfo> appList = applicationMapper.getAppList();
        for(AppInfo appInfo:appList){
            Map<String,Long> m = clickRecordService.countClickNum(appInfo.getAppId(),startTime);
            appInfo.setClickNum(m.get("clickNum"));
            appInfo.setPcClickNum(m.get("pcClickNum"));
            Map<String,Object> res = activationRecordService.countActivationNum(appInfo.getAppId(),startTime);
            appInfo.setActivationNum((long)res.get("activationNum"));
            long noticeNum = 0L;
            if(res.containsKey("noticeNum")){
                noticeNum= ((BigDecimal) res.get("noticeNum")).longValue();
            }
            appInfo.setNoticeNum(noticeNum);
            appInfo.setConversion(CommonUtil.getPercent(appInfo.getActivationNum(), appInfo.getPcClickNum()));
            applicationMapper.updateStatistics(appInfo);
        }
        logger.info("完成统计应用所有的>>>点击激活数据");
    }

    public void pressMonthStatisticsApp(String month){
        //查询应用列表
        List<AppInfo> appList = applicationMapper.getAppList();

        for(AppInfo appInfo:appList){

        }

    }


}
