package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于后台模板查询接口返回基本任务信息
 */
@Data
public class CrRoleConfigMission implements Serializable {

	@ApiModelProperty(value = "任务名称")
	private String title;

	@ApiModelProperty(value = "任务id")
	private String resourceId;

	@ApiModelProperty(value = "任务描述")
	private String description;
}
