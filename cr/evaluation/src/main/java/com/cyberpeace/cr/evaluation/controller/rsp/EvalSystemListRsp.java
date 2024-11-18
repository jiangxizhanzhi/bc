package com.cyberpeace.cr.evaluation.controller.rsp;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估系统列表响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalSystemListRsp {

	/**
	 * 评估系统id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 第一指数数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "一级指标数量", required = true)
	@NotNull
	private Integer firstIndexNum;

	/**
	 * 第二指数数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "二级指标数量", required = true)
	@NotNull
	private Integer secondIndexNum;

	/**
	 * 第三指数数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "三级指标数量", required = true)
	@NotNull
	private Integer thirdIndexNum;

}