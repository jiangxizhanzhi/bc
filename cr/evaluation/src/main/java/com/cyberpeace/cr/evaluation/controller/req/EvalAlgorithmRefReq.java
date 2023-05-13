package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EvalAlgorithmRefReq {

	@ApiModelProperty(value = "算法Id", required = true)
	private String resourceId;

	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;

	@ApiModelProperty(value = "算法模型-0-其他算法-1-层次分析法", required = true)
	@NotEmpty
	private Integer mode;

	@ApiModelProperty(value = "矩阵数据内容", required = true)
	@NotNull
	private String matrixData;


}
