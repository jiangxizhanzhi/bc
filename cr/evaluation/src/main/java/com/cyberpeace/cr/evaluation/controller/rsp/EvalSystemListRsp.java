package com.cyberpeace.cr.evaluation.controller.rsp;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估系统列表响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalSystemListRsp {

	/**
	 * 评估体系Id
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 一级指标数量
	 */
	@ApiModelProperty(value = "一级指标数量", required = true)
	@NotNull
	private Integer firstIndexNum;

	/**
	 * 二级指标数量
	 */
	@ApiModelProperty(value = "二级指标数量", required = true)
	@NotNull
	private Integer secondIndexNum;

	/**
	 * 三级指标数量
	 */
	@ApiModelProperty(value = "三级指标数量", required = true)
	@NotNull
	private Integer thirdIndexNum;

}