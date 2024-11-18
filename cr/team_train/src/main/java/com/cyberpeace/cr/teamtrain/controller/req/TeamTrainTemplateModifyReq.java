package com.cyberpeace.cr.teamtrain.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练模板修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see TeamTrainTemplateReq
 * @since 1.0.0
 */
@Data
public class TeamTrainTemplateModifyReq extends TeamTrainTemplateReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(required = true, value = "模板编号")
	@NotNull
	private String resourceId;
}
