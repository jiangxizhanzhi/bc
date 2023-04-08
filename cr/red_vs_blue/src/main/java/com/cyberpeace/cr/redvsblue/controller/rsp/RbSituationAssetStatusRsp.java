package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RbSituationAssetStatusRsp implements Serializable {
    @ApiModelProperty("网络节点")
    private AssetCount network;

    @ApiModelProperty("操作系统")
    private AssetCount operation;

    @ApiModelProperty("业务节点")
    private AssetCount application;

    @ApiModelProperty("安全节点")
    private AssetCount security;

    @Data
    public static class AssetCount {
        @ApiModelProperty("原始数")
        private Integer originalCount;

        @ApiModelProperty("变化数")
        private Integer changedCount;
    }
}
