package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalAlgorithmEditRsp extends EvalAlgorithmRef {

	@ApiModelProperty(value = "算法矩阵内容一致性")
	private boolean cr;

	@ApiModelProperty(value = "算法矩阵内容一致性返回信息")
	private String msg;
}
