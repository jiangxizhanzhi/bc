package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务启动请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalTaskStartReq {

	/**
	 * 靶场测评模板Id
	 */
	@ApiModelProperty(value = "靶场测评模板Id", required = true)
	@NotNull
	private String templateId;

}