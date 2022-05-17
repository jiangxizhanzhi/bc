package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskFlowListQuery extends PageReqBase implements Serializable {

	@ApiModelProperty("交互类型 0:背景流量， 1：攻击流量，2：全流量")
	private Integer interactiveType;

	@ApiModelProperty("测评靶场任务id")
	private String taskId;

	@ApiModelProperty("节点id")
	private String nodeId;

}
