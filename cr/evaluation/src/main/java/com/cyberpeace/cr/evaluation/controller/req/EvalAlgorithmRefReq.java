package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 评估算法裁判请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalAlgorithmRefReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "算法Id", required = true)
	private String resourceId;

	/**
	 * 代号
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;

	/**
	 * 模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "算法模型-0-其他算法-1-层次分析法", required = true)
	@NotEmpty
	private Integer mode;

	/**
	 * 矩阵数据
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "矩阵数据内容", required = true)
	@NotNull
	private String matrixData;


}
