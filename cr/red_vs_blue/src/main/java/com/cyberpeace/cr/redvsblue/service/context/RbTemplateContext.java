package com.cyberpeace.cr.redvsblue.service.context;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateContext extends BasicTemplate {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	@ApiModelProperty(required = true)
	@NotNull
	private String roleList;

}
