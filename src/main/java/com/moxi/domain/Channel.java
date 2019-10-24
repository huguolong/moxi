package com.moxi.domain;

import java.util.Date;

import com.moxi.model.BaseObject;
import lombok.Data;

@Data
public class Channel extends BaseObject {
    private Integer id;

    private String name;

    private String code;

    private Integer status;

    private String company;

    private Date createTime;
    
    private String describe;

    private String callbackRatio;



}