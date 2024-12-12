package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 伊娃模板修改请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvaTemplateModifyReq extends EvaTemplateAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "测评靶场模板id", required = true)
	@NotEmpty
	private String resourceId;

}