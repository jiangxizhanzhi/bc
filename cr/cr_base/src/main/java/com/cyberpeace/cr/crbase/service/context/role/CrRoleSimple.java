package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;

import lombok.Data;

/**
 * 角色简要信息
 *
 * @author SN
 * @date 2023/05/15
 */
@Data
public class CrRoleSimple implements Serializable {

	private String resourceId;

	private String name;

	private Integer roleGroup;

	private List<CrRoleMissionSchemeStage> missionScheme;

	private List<String> userIds;

	public CrRoleSimple(CrRole role) {
		this.resourceId = role.getResourceId();
		this.name = role.getName();
		this.roleGroup = role.getRoleGroup();
		this.missionScheme = JsonUtil.toJsonObjectArray(role.getMissionScheme(),
				CrRoleMissionSchemeStage.class);
		this.userIds = JsonUtil.toJsonObjectArray(role.getUserIds(), String.class);
	}

	public List<String> getMissionIds() {
		return StreamApiUtils.flatMap(missionScheme, CrRoleMissionSchemeStage::getMissions);
	}

	public Integer getTaskCount() {
		return this.userIds.size()
				* missionScheme.stream().mapToInt(stage -> stage.getMissions().size()).sum();
	}
}
