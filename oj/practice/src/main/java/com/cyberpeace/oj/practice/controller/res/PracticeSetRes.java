package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeSetRes implements Serializable {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("习题集类型 0=基础理论 2=实践操作")
    private Integer practiceType;

    @ApiModelProperty("是否公开")
    private boolean isPublic;

    @ApiModelProperty("创建人")
    private String createUserId;

    @ApiModelProperty("创建人名称")
    private String createUserName;

    @ApiModelProperty("习题数量")
    private String practiceCount;

}
