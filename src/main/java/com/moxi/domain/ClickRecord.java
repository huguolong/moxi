package com.moxi.domain;

import java.util.Date;

import com.moxi.model.BaseObject;
import lombok.Data;

@Data
public class ClickRecord extends BaseObject {
    private Integer id;

    private String reqUrl;

    private String reqParam;
    
    private String appId;
    
    private Integer channelId;

    private String idfa;

    private String ua;

    private String ip;

    private String result;

    private String callbackAddress;

    private Integer isActivation;

    private Date createTime;

    private String channelCode;
}