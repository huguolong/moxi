package com.moxi.domain;

import com.moxi.model.BaseObject;
import lombok.Data;

import java.util.Date;

@Data
public class AppChannel extends BaseObject {

    private int id;

    private int appInfoId;

    private int channelId;

    private int status;

    private String callbackRatio;

    private Date createTime;
}
