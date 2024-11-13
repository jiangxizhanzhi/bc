package com.cyberpeace.cr.crbase.service.context.guide;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用于后台模板查询接口返回基本任务信息
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class RoleConfigStageBo implements Serializable {

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "阶段名")
	private String stage;

	/**
	 * 任务
	 */
	@ApiModelProperty(value = "任务列表")
	private List<CrRoleConfigMission> missions;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;
}
