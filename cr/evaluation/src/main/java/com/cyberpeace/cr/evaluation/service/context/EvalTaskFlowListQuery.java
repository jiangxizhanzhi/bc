package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskFlowListQuery extends PageReqBase implements Serializable {

	@ApiModelProperty(name = "interactive_type", value = "交互类型 0:背景流量， 1：攻击流量，2：全流量")
	private Integer interactiveType;

	@ApiModelProperty(name = "task_id", value = "测评靶场任务id")
	private String taskId;

	@ApiModelProperty(name = "node_id", value = "节点id")
	private String nodeId;

	public void setInteractive_type(Integer interactiveType) {
		this.interactiveType = interactiveType;
	}

	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

	public void setNode_id(String nodeId) {
		this.nodeId = nodeId;
	}

}
