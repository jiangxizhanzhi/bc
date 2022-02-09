package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import lombok.Data;

@Data
public class BaseRole {

	private String resourceId;

	private String name;

	private Integer roleGroup;

	private Integer parentHash;

	private String description;

	private List<RoleUsers> roleDataList;

	private ChartInfo chartInfo;

	public void setRoleGroup(Integer roleGroup) {
		this.roleGroup = roleGroup;
	}

	public void setRoleGroup(CrRole.RoleGroup roleGroup) {
		this.roleGroup = roleGroup.getCode();
	}
}