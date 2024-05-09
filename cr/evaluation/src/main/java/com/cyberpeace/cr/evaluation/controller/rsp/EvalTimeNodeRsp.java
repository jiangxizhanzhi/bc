package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalTimeNodeRsp implements Serializable {

	@ApiModelProperty("阶段序号")
	private Integer stage;

	@ApiModelProperty("事件列表")
	private List<EvalStageTimeNodeRsp> stageNodes;
}
