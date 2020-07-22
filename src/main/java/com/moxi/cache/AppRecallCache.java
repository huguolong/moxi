package com.moxi.cache;

import com.moxi.domain.AppChannel;
import com.moxi.domain.AppInfo;
import com.moxi.domain.Channel;
import com.moxi.mapper.AppChannelMapper;
import com.moxi.mapper.ApplicationMapper;
import com.moxi.mapper.ChannelMapper;
import com.moxi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppRecallCache {

    @Resource
    private ApplicationMapper applicationMapper;
    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private AppChannelMapper appChannelMapper;

    private static Map<String, Integer> caches = new ConcurrentHashMap<>();

    public int getAppRecall(String appId,String channelCode) {
        int recall = 100;

        if(!StringUtils.isEmpty(channelCode)){

            //渠道与应用之间的缓存 key
            String acKey = channelCode+appId;
            if (this.isContains(acKey)) {
                recall = caches.get(acKey);
            }else{
                Map<String,String> param = new HashMap<>();
                param.put("channelCode",channelCode);
                param.put("appId",appId);
                AppChannel appChannel = appChannelMapper.findByCodeAndApp(param);
                if(null != appChannel){
                    recall = Integer.valueOf(appChannel.getCallbackRatio());
                    caches.put(acKey,recall);
                }
            }
        }else{
            if (this.isContains(appId)) {
                recall = caches.get(appId);
            }else{
                //从数据库中获取，然后存入缓存中
                AppInfo appInfo = new AppInfo();
                appInfo.setAppId(appId);
                AppInfo info = applicationMapper.findByAppId(appInfo);
                if(null != info){
                    recall = Integer.valueOf(info.getCallbackRatio());
                    caches.put(appId,recall);

                }
            }
        }
        return recall;
    }

    public void delAppRecall(String appId){
        caches.remove(appId);
    }

    /**
     * 判断是否在缓存中
     *
     * @param key
     * @return
     */
    public boolean isContains(String key) {
        return caches.containsKey(key);
    }

    public void delAppRecallAll(){
        caches = new ConcurrentHashMap<>();
    }

    public Map getAppRecal(){
        return caches;
    }
}