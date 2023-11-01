package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import com.cyberpeace.common.context.role.CrRoleNode;
import com.cyberpeace.common.enums.RoleGroup;
import com.cyberpeace.cr.crbase.service.context.ColorMission;

import lombok.Data;

/**
 * 团队训练角色列表
 */
@Data
public class RoleListContext<T> {

	private String Id;

	private String resourceId;

	private String name;

	private String description;

	private Integer roleGroup;

	private List<CrRoleNode> assetNodes;

	private List<String> children;

	private List<CrRoleNode> visibleNodes;

	private String key;

	private String users;

	private String type;

	private ColorMission<T> missionsScheme;

	public void setRoleGroup(Integer roleGroup) {
		this.roleGroup = roleGroup;
	}

	public void setRoleGroup(RoleGroup roleGroup) {
		this.roleGroup = roleGroup.getCode();
	}

}
