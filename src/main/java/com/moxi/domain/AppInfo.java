package com.moxi.domain;

import java.util.Date;

import com.moxi.model.BaseObject;

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
    private Integer activationNum = 0;
    /**
     * 转化率
     */
    private String conversion = "0%";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCallbackRatio() {
        return callbackRatio;
    }

    public void setCallbackRatio(String callbackRatio) {
        this.callbackRatio = callbackRatio;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqParam() {
        return reqParam;
    }

    public void setReqParam(String reqParam) {
        this.reqParam = reqParam;
    }

    public String getReqRatio() {
        return reqRatio;
    }

    public void setReqRatio(String reqRatio) {
        this.reqRatio = reqRatio;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}


	public Long getClickNum() {
		return clickNum;
	}

	public void setClickNum(Long clickNum) {
		this.clickNum = clickNum;
	}

	public Long getPcClickNum() {
		return pcClickNum;
	}

	public void setPcClickNum(Long pcClickNum) {
		this.pcClickNum = pcClickNum;
	}

	public Integer getActivationNum() {
		return activationNum;
	}

	public void setActivationNum(Integer activationNum) {
		this.activationNum = activationNum;
	}

	public String getConversion() {
		return conversion;
	}

	public void setConversion(String conversion) {
		this.conversion = conversion;
	}
    
	
    
}