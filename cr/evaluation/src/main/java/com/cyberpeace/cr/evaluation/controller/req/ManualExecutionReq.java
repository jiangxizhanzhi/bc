package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ManualExecutionReq implements Serializable {

	@ApiModelProperty("任务id")
	private String missionId;

	@ApiModelProperty("节点id")
	private String nodeId;

	@ApiModelProperty("命令")
	private String command;

	@ApiModelProperty("工具")
	private String tool;

	private List<String> ids;
}
