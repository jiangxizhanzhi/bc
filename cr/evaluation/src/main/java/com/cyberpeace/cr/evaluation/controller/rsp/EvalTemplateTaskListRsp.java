package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估模板任务列表响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTemplateTaskListRsp {

	/**
	 * 当前测评任务列表
	 */
	@ApiModelProperty(value = "当前测评任务列表")
	private List<EvalTaskSlimContext> taskList;

	/**
	 * 当前测评任务
	 */
	@ApiModelProperty(value = "当前测评任务")
	private String currentTaskId;

}
