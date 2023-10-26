package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AlgorithmParamContext implements Serializable {

	@ApiModelProperty(value = "参数名称", required = true)
	@NotNull
	private String paramName;

	@ApiModelProperty(value = "描述", required = false)
	private String desc;

	@ApiModelProperty(value = "参数类型", required = true)
	@NotNull
	private String paramType;

	@ApiModelProperty(value = "默认值", required = true)
	@NotNull
	private String defaultValue;
}
