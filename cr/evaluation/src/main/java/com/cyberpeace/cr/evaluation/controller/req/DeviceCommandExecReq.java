package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeviceCommandExecReq {
	@ApiModelProperty(value = "节点id")
	private String nodeId;
	@ApiModelProperty(value = "场景id")
	private String id;
	@ApiModelProperty(value = "需要执行的指令")
	private List<String> execCmds;
	@ApiModelProperty(value = "执行的协议方式")
	private String protocol;
	@ApiModelProperty(value = "需要执行的指令模板")
	private List<String> commandTemplateIds;
	@ApiModelProperty(value = "需要执行的指令参数")
	private List<String> commandParameters;
}
