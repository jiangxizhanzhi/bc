package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估AHP算法请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalAhpAlgorithmReq {

	/**
	 * 评估算法id
	 */
	@ApiModelProperty(value = "算法Id", required = false)
	private String evalAlgorithmId;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = true)
	private String matrixData;
}
