package com.cyberpeace.cr.scene_manage.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("场景管理模板")
public class SceneTrainTemplateReqRel {

	@ApiModelProperty(value = "基础数据", required = true)
	@NotNull
	private EventDataReq eventData;

	@ApiModelProperty("任务方案")
	private CrRoleConfigMissionsScheme missionsScheme;
}
