package com.cyberpeace.oj.person.controller.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SystemRecordRes implements Serializable {

    private Date createTime;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty("操作类型")
    Integer method;

    @ApiModelProperty("操作对象")
    String target;

    @ApiModelProperty("在线时间")
    private double time;
}
