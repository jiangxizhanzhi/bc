package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 伊娃模板修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see EvaTemplateAddReq
 * @since 1.0.0
 */
@Data
public class EvaTemplateModifyReq extends EvaTemplateAddReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评靶场模板id", required = true)
	@NotEmpty
	private String resourceId;

}