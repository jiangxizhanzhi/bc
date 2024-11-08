package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估算法编辑响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalAlgorithmEditRsp extends EvalAlgorithmRef {

	/**
	 * 靶场
	 */
	@ApiModelProperty(value = "算法矩阵内容一致性")
	private boolean cr;

	/**
	 * 味精
	 */
	@ApiModelProperty(value = "算法矩阵内容一致性返回信息")
	private String msg;
}
