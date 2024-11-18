package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 评估记录节点探针请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalRecordNodeProbeReq implements Serializable {
    /**
     * id
     *
     * @see String
     */
    @ApiModelProperty("场景节点ID")
    private String id;

    /**
     * 标签
     *
     * @see String
     */
    @ApiModelProperty("场景节点名称")
    private String label;

    /**
     * 数据
     *
     * @see List<EvalRoomPairRsp>
     */
    @ApiModelProperty("场景节点探针数据")
    private List<EvalRoomPairRsp> data;
}
