package com.moxi.cache;

import com.moxi.domain.AppInfo;
import com.moxi.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppRecallCache {

    @Resource
    private ApplicationMapper applicationMapper;

    private static Map<String, Integer> caches = new ConcurrentHashMap<String, Integer>();

    public int getAppRecall(String appId) {
        int recall = 100;
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