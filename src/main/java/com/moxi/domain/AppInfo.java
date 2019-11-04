package com.moxi.domain;

import java.util.Date;

import com.moxi.model.BaseObject;
import lombok.Data;

@Data
public class AppInfo extends BaseObject {
    private Integer id;

    private Integer channelId;

    private String appId;

    private String appName;

    private String callbackRatio;

    private String reqType;

    private String reqUrl;

    private String reqParam;

    private String reqRatio;

    private Byte status;

    private Date createTime;
    
    /**
     * 渠道
     */
    private String channelName;
    
    /**
     * 点击数
     */
    private Long clickNum = 0L;
    /**
     * 排重点击数
     */
    private Long pcClickNum = 0L;
    /**
     * 激活数
     */
    private Long activationNum = 0L;
    /**
     * 转化率
     */
    private String conversion = "0%";
    /**
     * 通知数
     */
    private Long noticeNum = 0L;

}