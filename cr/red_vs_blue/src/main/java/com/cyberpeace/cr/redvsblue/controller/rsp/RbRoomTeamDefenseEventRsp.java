package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RbRoomTeamDefenseEventRsp implements Serializable {
    @ApiModelProperty("防御资产ID")
    private String assetId;

    @ApiModelProperty("防御资产名称")
    private String assetName;

    @ApiModelProperty("防御时间")
    private Date defenseTime;

    @ApiModelProperty("防御类型")
    private Integer defenseType;
}
