package com.cyberpeace.cr.teamtrain.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;

import lombok.Data;

/**
 * 团队训练角色列表
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoleListContext<T> implements Serializable {

	/**
	 * id
	 */
	private String Id;

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 角色组
	 */
	private Integer roleGroup;

	/**
	 * 资产节点
	 */
	private List<CrRoleNode> assetNodes;

	/**
	 * 子级
	 */
	private List<String> children;

	/**
	 * 可见节点
	 */
	private List<CrRoleNode> visibleNodes;

	/**
	 * 钥匙
	 */
	private String key;

	/**
	 * 用户
	 */
	private String users;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 任务计划
	 */
	private ColorMission<T> missionsScheme;

	public void setRoleGroup(Integer roleGroup) {
		this.roleGroup = roleGroup;
	}

	public void setRoleGroup(CrRole.RoleGroup roleGroup) {
		this.roleGroup = roleGroup.getCode();
	}

}
