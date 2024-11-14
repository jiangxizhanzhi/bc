package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务停止请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTaskStopReq {

	/**
	 * 靶场测评模板Id
	 */
	@ApiModelProperty(value = "靶场测评模板Id", required = true)
	@NotNull
	private String templateId;

	/**
	 * 测评任务Id
	 */
	@ApiModelProperty(value = "测评任务Id", required = true)
	private String taskId;

	/**
	 * 测评任务名称
	 */
	@ApiModelProperty(value = "测评任务名称", required = false)
	private String taskName;

}