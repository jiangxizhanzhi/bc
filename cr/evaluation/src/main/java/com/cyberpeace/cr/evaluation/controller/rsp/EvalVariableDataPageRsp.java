package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalVariableDataPageRsp implements Serializable {
    @ApiModelProperty("场景节点IP")
    private String nodeIp;

    @ApiModelProperty("探针数据采集时间")
    private String collectionTime;

    @ApiModelProperty("探针采集数据")
    private String content;
}
