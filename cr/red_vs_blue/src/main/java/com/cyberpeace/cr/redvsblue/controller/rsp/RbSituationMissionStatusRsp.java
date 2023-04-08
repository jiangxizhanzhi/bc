package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RbSituationMissionStatusRsp implements Serializable {
    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("任务标题")
    private String missionTitle;

    @ApiModelProperty("任务状态")
    private Integer status;
}
