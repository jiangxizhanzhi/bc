package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CrRoomTeamAssetInfoRsp implements Serializable {
    @ApiModelProperty("原始资产数量")
    private Integer originNum;

    @ApiModelProperty("变更资产数量")
    private Integer changedNum;
}
