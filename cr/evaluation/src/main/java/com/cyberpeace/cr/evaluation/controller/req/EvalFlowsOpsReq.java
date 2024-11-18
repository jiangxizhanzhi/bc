package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.scene.FlowInfoContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估流量操作 计划请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalFlowsOpsReq implements Serializable {

	/**
	 * 任务id
	 *
	 * @see String
	 */
	@ApiModelProperty("测评靶场任务id")
	@NotNull
	private String taskId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("节点id")
	@NotNull
	private String nodeId;

	/**
	 * 运行类型
	 */
	@ApiModelProperty(value = "执行方式：0 串行，1 并行")
	@NotNull
	private int runType;

	/**
	 * 流量
	 *
	 * @see List<FlowInfoContext>
	 */
	@ApiModelProperty("流量列表")
	private List<FlowInfoContext> flows;
}
