package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务停请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTaskStopReq {

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "靶场测评模板Id", required = true)
	@NotNull
	private String templateId;

	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "测评任务Id", required = true)
	@NotNull
	private String taskId;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "测评任务名称", required = false)
	private String taskName;

}