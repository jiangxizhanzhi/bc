package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalAlgorithmRsp {

	@ApiModelProperty(value = "算法Id", required = true)
	@NotNull
	private String evalAlgorithmId;

	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(value = "描述", required = false)
	private String description;

	@ApiModelProperty(value = "算法类型", required = true)
	@NotNull
	private Integer type;

	@ApiModelProperty(value = "算法语言", required = true)
	@NotNull
	private Integer algorithmLanguage;

	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;

	@ApiModelProperty(value = "矩阵代码", required = true)
	private String matrixData;

	@ApiModelProperty(value = "算法类型", required = true)
	@NotNull
	private Integer mode;

	@ApiModelProperty(value = "是否为内置算法", required = true)
	@NotNull
	private Integer builtin;

	@ApiModelProperty(value = "参数列表", required = true)
	@NotNull
	private List<AlgorithmParamContext> params = Lists.newArrayList();

	@ApiModelProperty(value = "参数个数", required = true)
	@NotNull
	private Integer paramsNum;

}
