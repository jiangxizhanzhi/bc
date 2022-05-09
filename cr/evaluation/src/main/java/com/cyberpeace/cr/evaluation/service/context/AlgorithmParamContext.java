package com.cyberpeace.cr.evaluation.service.context;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AlgorithmParamContext {

	@ApiModelProperty(value = "参数名称", required = true)
	@NotNull
	private String paramName;

	@ApiModelProperty(value = "描述", required = true)
	@NotNull
	private String desc;

	@ApiModelProperty(value = "参数类型", required = true)
	@NotNull
	private String paramType;

	@ApiModelProperty(value = "默认值", required = true)
	@NotNull
	private String defaultValue;
}
