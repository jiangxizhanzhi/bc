package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估算法编辑响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalAlgorithmEditRsp extends EvalAlgorithmRef {

	/**
	 * 算法矩阵内容一致性
	 */
	@ApiModelProperty(value = "算法矩阵内容一致性")
	private boolean cr;

	/**
	 * 算法矩阵内容一致性返回信息
	 */
	@ApiModelProperty(value = "算法矩阵内容一致性返回信息")
	private String msg;
}
