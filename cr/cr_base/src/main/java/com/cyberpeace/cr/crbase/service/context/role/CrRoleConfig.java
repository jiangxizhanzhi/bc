package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import com.cyberpeace.common.context.role.CrRoleNodesData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于后台模板查询的返回
 */
@Data
public class CrRoleConfig extends CrRoleNodesData implements Serializable {

	@ApiModelProperty(value = "角色名称")
	private String name;

	/**
	 * 辅助前端切换不同角色任务编排的字段
	 */
	@ApiModelProperty(value = "辅助前端切换不同角色任务编排的字段")
	private String id;

	@ApiModelProperty(value = "任务配置")
	private CrRoleConfigMissionsScheme missionsScheme;
}
