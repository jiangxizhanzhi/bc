package com.cyberpeace.cr.crbase.service.context.guide;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用于后台模板查询接口返回基本任务信息
 */
@Data
public class RoleConfigStageBo implements Serializable {

	@ApiModelProperty(value = "阶段名")
	private String stage;

	@ApiModelProperty(value = "任务列表")
	private List<CrRoleConfigMission> missions;

	@ApiModelProperty(value = "角色名称")
	private String roleName;
}
