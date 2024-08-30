package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalAlarmConditionAddReq implements Serializable {
    @ApiModelProperty(value = "报警条件的类型")
    private String type;

    @ApiModelProperty(value = "聚合类型")
    private String aggregationType;

    @ApiModelProperty(value = "聚合类型名称")
    private String aggregationTypeName;

    @ApiModelProperty(value = "运算符")
    private String operator;

    @ApiModelProperty(value = "运算符名称")
    private String operatorName;

    @ApiModelProperty(value = "阈值")
    private double threshold;
}