package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.scene.FlowInfoContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalFlowsOpsReq implements Serializable {

	@ApiModelProperty("测评靶场任务id")
	@NotNull
	private String taskId;

	@ApiModelProperty("节点id")
	@NotNull
	private String nodeId;

	@ApiModelProperty(value = "执行方式：0 串行，1 并行")
	@NotNull
	private int runType;

	@ApiModelProperty("流量列表")
	private List<FlowInfoContext> flows;
}
