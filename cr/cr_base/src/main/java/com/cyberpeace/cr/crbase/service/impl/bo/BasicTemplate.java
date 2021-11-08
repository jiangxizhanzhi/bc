package com.cyberpeace.cr.crbase.service.impl.bo;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasicTemplate {

	@ApiModelProperty(required = true)
	@NotNull
	private BaseInfo baseInfo;
}
