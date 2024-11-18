package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估模板任务列表响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalTemplateTaskListRsp implements Serializable {

	/**
	 * 任务列表
	 *
	 * @see List<EvalTaskSlimContext>
	 */
	@ApiModelProperty(value = "当前测评任务列表")
	private List<EvalTaskSlimContext> taskList;

	/**
	 * 当前任务id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "当前测评任务")
	private String currentTaskId;

}
