package com.cyberpeace.cr.crbase.service.impl.bo;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasicTemplate {

	@ApiModelProperty(value = "基础信息", required = true)
	@NotNull
	private BaseInfo baseInfo;
}
