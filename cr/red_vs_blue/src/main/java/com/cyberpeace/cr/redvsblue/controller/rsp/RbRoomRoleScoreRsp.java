package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RbRoomRoleScoreRsp implements Serializable {

    @ApiModelProperty("角色id")
    private String id;

    @ApiModelProperty("角色名")
    private String name;

    @ApiModelProperty("角色得分")
    private Integer score;

    @ApiModelProperty("百分比")
    private Double percent;
}
