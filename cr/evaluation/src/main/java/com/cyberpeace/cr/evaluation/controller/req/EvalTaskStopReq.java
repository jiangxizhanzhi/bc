package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务停请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalTaskStopReq {

	/**
	 * 模板id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "靶场测评模板Id", required = true)
	@NotNull
	private String templateId;

	/**
	 * 任务id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评任务Id", required = true)
	@NotNull
	private String taskId;

	/**
	 * 任务名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评任务名称", required = false)
	private String taskName;

}