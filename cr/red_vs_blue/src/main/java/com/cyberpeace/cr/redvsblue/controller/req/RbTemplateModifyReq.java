package com.cyberpeace.cr.redvsblue.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB模板修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see RbTemplateAddReq
 * @since 1.0.0
 */
@Data
public class RbTemplateModifyReq extends RbTemplateAddReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(required = true, value = "模板id")
	@NotNull
	private String resourceId;
}
