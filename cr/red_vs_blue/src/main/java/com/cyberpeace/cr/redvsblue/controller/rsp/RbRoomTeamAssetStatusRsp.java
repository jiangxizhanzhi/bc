package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RbRoomTeamAssetStatusRsp implements Serializable {
    @ApiModelProperty("网络设备")
    private CrRoomTeamAssetInfoRsp network;

    @ApiModelProperty("操作系统")
    private CrRoomTeamAssetInfoRsp operating;

    @ApiModelProperty("应用系统")
    private CrRoomTeamAssetInfoRsp application;

    @ApiModelProperty("安全设备")
    private CrRoomTeamAssetInfoRsp security;
}
