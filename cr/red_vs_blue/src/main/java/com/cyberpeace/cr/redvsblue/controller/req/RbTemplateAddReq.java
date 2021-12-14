package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateAddReq<T> extends BasicTemplate {

	@ApiModelProperty(required = true)
	@NotNull
	private List<RoleListReq<T>> redRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private List<RoleListReq<T>> blueRoles;

}