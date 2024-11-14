package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评模板修改请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvaTemplateModifyReq extends EvaTemplateAddReq {

	/**
	 * 测评靶场模板id
	 */
	@ApiModelProperty(value = "测评靶场模板id", required = true)
	@NotEmpty
	private String resourceId;

}