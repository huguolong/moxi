package com.moxi.cache;

import com.moxi.domain.AppInfo;
import com.moxi.domain.Channel;
import com.moxi.mapper.ApplicationMapper;
import com.moxi.mapper.ChannelMapper;
import com.moxi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppRecallCache {

    @Resource
    private ApplicationMapper applicationMapper;
    @Resource
    private ChannelMapper channelMapper;

    private static Map<String, Integer> caches = new ConcurrentHashMap<String, Integer>();

    public int getAppRecall(String appId,String channelCode) {
        int recall = 100;
        if(!StringUtils.isEmpty(channelCode)){
            if (this.isContains(channelCode)) {
                recall = caches.get(channelCode);
            }else{
                Channel channel = new Channel();
                channel.setCode(channelCode);
                channel = channelMapper.findByCode(channel);
                if(null != channel){
                    recall = Integer.valueOf(channel.getCallbackRatio());
                    caches.put(channelCode,recall);
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
}