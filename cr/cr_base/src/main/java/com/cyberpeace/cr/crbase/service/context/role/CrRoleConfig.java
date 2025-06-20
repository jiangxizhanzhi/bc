package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用于后台模板查询的返回
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CrRoleConfig extends CrRoleNodesData implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String name;

	/**
	 * 辅助前端切换不同角色任务编排的字段
	 */
	@ApiModelProperty(value = "辅助前端切换不同角色任务编排的字段")
	private String id;

	/**
	 * 任务计划
	 */
	@ApiModelProperty(value = "任务配置")
	private CrRoleConfigMissionsScheme missionsScheme;
}
