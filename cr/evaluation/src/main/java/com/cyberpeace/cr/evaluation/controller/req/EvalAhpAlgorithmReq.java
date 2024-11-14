package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估AHP算法请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalAhpAlgorithmReq {

	/**
	 * 算法Id
	 */
	@ApiModelProperty(value = "算法Id", required = false)
	private String evalAlgorithmId;

	/**
	 * 层次分析法json数组
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = true)
	private String matrixData;
}
