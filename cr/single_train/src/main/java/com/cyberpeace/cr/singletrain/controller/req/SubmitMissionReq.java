package com.cyberpeace.cr.singletrain.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 提交任务请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("任务提交")
public class SubmitMissionReq implements Serializable {

    /**
     * 任务id
     */
    @NotNull
    @ApiModelProperty("任务id")
    private String missionResourceId;

    /**
     * 记录内容
     */
    @NotNull
    @ApiModelProperty("记录内容")
    private String trainingRecord;

    /**
     * 用户提交答案
     */
    @NotNull
    @ApiModelProperty("用户提交答案")
    private String answer;

    /**
     * 业务模板ID
     */
    @NotNull
    @ApiModelProperty("业务模板ID")
    private String businessTemplateId;
}
