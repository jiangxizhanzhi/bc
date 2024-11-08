package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 评估算法裁判请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalAlgorithmRefReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "算法Id", required = true)
	private String resourceId;

	/**
	 * 代号
	 */
	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;

	/**
	 * 模式
	 */
	@ApiModelProperty(value = "算法模型-0-其他算法-1-层次分析法", required = true)
	@NotEmpty
	private Integer mode;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "矩阵数据内容", required = true)
	@NotNull
	private String matrixData;


}
