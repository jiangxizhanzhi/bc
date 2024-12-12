package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务步请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTaskStepReq {

	/**
	 * 步id
	 */
	@ApiModelProperty(value = "测评步骤Id", required = true)
	@NotNull
	private String stepId;

}