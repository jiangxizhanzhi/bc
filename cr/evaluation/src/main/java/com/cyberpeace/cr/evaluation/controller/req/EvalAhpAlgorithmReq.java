package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估AHP算法请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalAhpAlgorithmReq {

	/**
	 * 评估算法id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "算法Id", required = false)
	private String evalAlgorithmId;

	/**
	 * 矩阵数据
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = true)
	private String matrixData;
}
