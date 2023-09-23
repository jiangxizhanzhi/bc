package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRecordNodeProbeReq implements Serializable {
    @ApiModelProperty("场景节点ID")
    private String id;

    @ApiModelProperty("场景节点名称")
    private String label;

    @ApiModelProperty("场景节点探针数据")
    private List<EvalRoomPairRsp> data;
}
