package com.cyberpeace.cr.teamtrain.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练模板修改请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TeamTrainTemplateModifyReq extends TeamTrainTemplateReq {

	/**
	 * 模板编号
	 */
	@ApiModelProperty(required = true, value = "模板编号")
	@NotNull
	private String resourceId;
}
