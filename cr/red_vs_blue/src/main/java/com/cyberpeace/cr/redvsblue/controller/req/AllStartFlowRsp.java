package com.cyberpeace.cr.redvsblue.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AllStartFlowRsp implements Serializable {
    @ApiModelProperty("训练实例Id")
    private String roomId;

    @ApiModelProperty("节点Id")
    private String nodeId;

    @ApiModelProperty("启动类型 0-串行;1-并行")
    private Integer runType;
}
