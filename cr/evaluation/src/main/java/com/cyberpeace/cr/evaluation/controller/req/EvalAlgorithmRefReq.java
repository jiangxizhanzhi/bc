package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估算法裁判请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalAlgorithmRefReq {

	/**
	 * 算法Id
	 */
	@ApiModelProperty(value = "算法Id", required = true)
	private String resourceId;

	/**
	 * 代码
	 */
	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;

	/**
	 * 算法模型-0-其他算法-1-层次分析法
	 */
	@ApiModelProperty(value = "算法模型-0-其他算法-1-层次分析法", required = true)
	@NotEmpty
	private Integer mode;

	/**
	 * 矩阵数据内容
	 */
	@ApiModelProperty(value = "矩阵数据内容", required = true)
	@NotNull
	private String matrixData;


}
