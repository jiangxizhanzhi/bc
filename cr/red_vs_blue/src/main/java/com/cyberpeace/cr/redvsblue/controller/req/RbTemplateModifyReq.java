package com.cyberpeace.cr.redvsblue.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB模板修改请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbTemplateModifyReq extends RbTemplateAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(required = true, value = "模板id")
	@NotNull
	private String resourceId;
}
