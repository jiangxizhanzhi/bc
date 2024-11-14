package com.cyberpeace.cr.redvsblue.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗模板修改请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbTemplateModifyReq extends RbTemplateAddReq {

	/**
	 * 模板id
	 */
	@ApiModelProperty(required = true, value = "模板id")
	@NotNull
	private String resourceId;
}
