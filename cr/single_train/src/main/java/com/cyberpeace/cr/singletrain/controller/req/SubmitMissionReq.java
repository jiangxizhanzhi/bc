package com.cyberpeace.cr.singletrain.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("任务提交")
public class SubmitMissionReq implements Serializable {

    @NotNull
    @ApiModelProperty("任务id")
    private String missionResourceId;

    @NotNull
    @ApiModelProperty("记录内容")
    private String trainingRecord;

    @NotNull
    @ApiModelProperty("用户提交答案")
    private String answer;

    @NotNull
    @ApiModelProperty("业务模板ID")
    private String businessTemplateId;
}
