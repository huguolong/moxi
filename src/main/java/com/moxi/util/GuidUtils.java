package com.moxi.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class GuidUtils {

    public String appKey;
    /**
     * @description:随机获取key值
     * @return
     */
    public String guid() {
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString().replace("-","");
        return key;
    }
    /**
     * 这是其中一个url的参数，是GUID的，全球唯一标志符
     * @return
     */
    public String appKey() {
        GuidUtils g = new GuidUtils();
        String guid = g.guid();
        appKey = guid;
        return appKey;
    }
    /**
     * 根据md5加密
     * @return
     */
    public String appScrect() {
        String mw = "key" + appKey ;
        String appSign = DigestUtils.md5Hex(mw.toString()).toUpperCase();// 得到以后还要用MD5加密。
        return appSign;
    }

    /**
     * 生成一个 token secret
     * @param args
     */
    public static void main(String[] args) {
        GuidUtils gd = new GuidUtils();
        String appKey=gd.appKey();
        System.out.println("app_key: "+appKey);
        String appScrect=gd.appScrect();
        System.out.println("app_screct: "+appScrect);
    }
}
