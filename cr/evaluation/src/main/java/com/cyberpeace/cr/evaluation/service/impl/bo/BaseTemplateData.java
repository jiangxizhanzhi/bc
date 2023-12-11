package com.cyberpeace.cr.evaluation.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseTemplateData implements Serializable {

	@ApiModelProperty("试验名称")
	private String name;

	@ApiModelProperty("试验目标")
	private String target;

	@ApiModelProperty("logo")
	private String logo;

	@ApiModelProperty("试验条件")
	private String condition;

	@ApiModelProperty("试验方法")
	private String method;

	@ApiModelProperty("测试类型")
	private Integer type;

}
