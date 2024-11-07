package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 评估记录节点探针请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalRecordNodeProbeReq implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("场景节点ID")
    private String id;

    /**
     * 标签
     */
    @ApiModelProperty("场景节点名称")
    private String label;

    /**
     * 数据
     */
    @ApiModelProperty("场景节点探针数据")
    private List<EvalRoomPairRsp> data;
}
