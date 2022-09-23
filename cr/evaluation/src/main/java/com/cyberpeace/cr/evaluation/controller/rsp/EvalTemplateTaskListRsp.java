package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalRoleConfig;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateTaskListRsp implements Serializable {

	@ApiModelProperty(value = "当前测评任务列表")
	private List<EvalTaskSlimContext> taskList;

	@ApiModelProperty(value = "当前测评任务")
	private String currentTaskId;

	@ApiModelProperty(value = "当前测评任务角色配置")
	private List<EvalRoleConfig> roleConfigs;
	
}
