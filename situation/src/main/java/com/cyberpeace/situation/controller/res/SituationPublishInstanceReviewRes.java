package com.cyberpeace.situation.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SituationPublishInstanceReviewRes implements Serializable {

    @ApiModelProperty(value = "资源id")
    private String resourceId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private String category;

    @ApiModelProperty(value = "大屏状态")
    private String status;

    @ApiModelProperty(value = "想定方案名称")
    private String planName;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

    @ApiModelProperty(value = "想定方案id")
    private String planId;

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "科目id")
    private String subjectId;

    @ApiModelProperty(value = "模板内容")
    private DashboardTemplateRes templateInfo;
}
