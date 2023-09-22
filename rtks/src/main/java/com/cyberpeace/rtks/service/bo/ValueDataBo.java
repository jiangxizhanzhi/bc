package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("计算中间存储")
public class ValueDataBo implements Serializable {

    @ApiModelProperty("资源编号")
    private String resourceId;

    @ApiModelProperty("技能或者知识点或者课程名称或者代号")
    private String content;

    @ApiModelProperty("进度或者得分")
    private Double process = (double) 0;

    @ApiModelProperty("权重")
    private Double weight = 1.0;

    @JsonIgnore
    private Double lessonCount = (double) 0;

    @JsonIgnore
    private Double completeCount = (double) 0;

}
