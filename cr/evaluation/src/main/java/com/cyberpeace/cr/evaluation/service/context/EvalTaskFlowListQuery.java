package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评任务流量列表查询
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTaskFlowListQuery extends PageReqBase implements Serializable {

	@ApiModelProperty(name = "flow_id", value = "流量ID")
	private String flowId;

	@ApiModelProperty(name = "is_all_flow", value = "所有流量")
	private Boolean isAllFlow;

	/**
	 * 交互类型 0:背景流量， 1：攻击流量，2：全流量
	 */
	@ApiModelProperty(name = "interactive_type", value = "交互类型 0:背景流量， 1：攻击流量，2：全流量")
	private Integer interactiveType;

	/**
	 * 测评靶场任务id
	 */
	@ApiModelProperty(name = "task_id", value = "测评靶场任务id")
	private String taskId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(name = "node_id", value = "节点id")
	private String nodeId;

	/**
	 * 设置互动类型
	 *
	 * @param interactiveType 交互式类型
	 */
	public void setInteractive_type(Integer interactiveType) {
		this.interactiveType = interactiveType;
	}

	/**
	 * 设置任务id
	 *
	 * @param taskId 任务id
	 */
	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 设置节点id
	 *
	 * @param nodeId 节点id
	 */
	public void setNode_id(String nodeId) {
		this.nodeId = nodeId;
	}
	public void setFlow_id(String flowId) {
		this.flowId = flowId;
	}
	public void setIs_all_flow(Boolean isAllFlow) {
		this.isAllFlow = isAllFlow;
	}

}
