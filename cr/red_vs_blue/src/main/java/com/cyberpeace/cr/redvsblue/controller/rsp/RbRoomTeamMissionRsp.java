package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RbRoomTeamMissionRsp implements Serializable {
    @ApiModelProperty("任务ID")
    private String missionId;

    @ApiModelProperty("任务名称")
    private String missionName;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("任务状态")
    private Integer missionStatus;
}
