package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估记录节点探针请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRecordNodeProbeReq implements Serializable {
	/**
	 * 场景节点ID
	 */
	@ApiModelProperty("场景节点ID")
    private String id;

	/**
	 * 场景节点名称
	 */
	@ApiModelProperty("场景节点名称")
    private String label;

	/**
	 * 场景节点探针数据
	 */
	@ApiModelProperty("场景节点探针数据")
    private List<EvalRoomPairRsp> data;
}
