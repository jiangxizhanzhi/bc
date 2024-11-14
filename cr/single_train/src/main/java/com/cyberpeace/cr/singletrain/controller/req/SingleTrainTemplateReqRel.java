package com.cyberpeace.cr.singletrain.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单训练模板请求体相对
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("单兵训练模板")
public class SingleTrainTemplateReqRel {

	/**
	 * 基础数据
	 */
	@ApiModelProperty(value = "基础数据", required = true)
	@NotNull
	private EventDataReq eventData;

	/**
	 * 任务方案
	 */
	@ApiModelProperty("任务方案")
	private CrRoleConfigMissionsScheme missionsScheme;
}
