package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateTaskListRsp {

	@ApiModelProperty(value = "当前测评任务列表")
	private List<EvalTaskSlimContext> taskList;

	@ApiModelProperty(value = "当前测评任务")
	private String currentTaskId;

}
