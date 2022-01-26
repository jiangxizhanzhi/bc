package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import lombok.Data;

/**
 * 用于后台模板查询的返回
 */
@Data
public class CrRoleConfig extends CrRoleNodesData implements Serializable {

	private String name;

	/**
	 * 辅助前端切换不同角色任务编排的字段
	 */
	private String id;

	private CrRoleConfigMissionsScheme missionsScheme;
}
