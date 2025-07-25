package com.cyberpeace.cr.redvsblue.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UniteFlowAttackReq implements Serializable {
    /**
     * 任务id
     */
    @ApiModelProperty(value = "任务id")
    private String taskId;

    /**
     * 源id
     */
    @ApiModelProperty(value = "源id")
    private String attackerId;

    /**
     * 目的id
     */
    @ApiModelProperty(value = "目的id")
    private String targetId;

    /**
     * 攻击流量
     */
    @ApiModelProperty(value = "流量包大小")
    private Integer size;
    /**
     * 攻击次数
     */
    @ApiModelProperty(value = "类型")
    private Integer type;
}
