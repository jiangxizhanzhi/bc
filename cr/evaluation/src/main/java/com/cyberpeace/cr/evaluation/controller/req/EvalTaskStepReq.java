package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务步骤请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTaskStepReq {

	/**
	 * 测评步骤Id
	 */
	@ApiModelProperty(value = "测评步骤Id", required = true)
	@NotNull
	private String stepId;

}