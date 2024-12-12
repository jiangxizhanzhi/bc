package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务开始请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTaskStartReq {

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "靶场测评模板Id", required = true)
	@NotNull
	private String templateId;

}