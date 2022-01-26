package com.cyberpeace.cr.singletrain.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单兵训练模板")
public class SingleTrainTemplateReqRel {

	@ApiModelProperty(value = "基础数据", required = true)
	@NotNull
	private EventDataReq eventData;

	@ApiModelProperty("任务方案")
	private CrRoleConfigMissionsScheme missionsScheme;
}
