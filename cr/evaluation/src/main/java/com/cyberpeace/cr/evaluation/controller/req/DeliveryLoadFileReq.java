package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryLoadFileReq {
    @ApiModelProperty(value = "启动场景id")
    private String sceneId;

    @ApiModelProperty(value = "载荷节点id")
    private String loadNodeId;

    @ApiModelProperty(value = "目标节点id")
    private String targetNodeId;
}
