package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于后台模板查询的返回
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see CrRoleNodesData
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CrRoleConfig extends CrRoleNodesData implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色名称")
	private String name;

	/**
	 * 辅助前端切换不同角色任务编排的字段
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "辅助前端切换不同角色任务编排的字段")
	private String id;

	/**
	 * 任务计划
	 *
	 * @see CrRoleConfigMissionsScheme
	 */
	@ApiModelProperty(value = "任务配置")
	private CrRoleConfigMissionsScheme missionsScheme;
}
