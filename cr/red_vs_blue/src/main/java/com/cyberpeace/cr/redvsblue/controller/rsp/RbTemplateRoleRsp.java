package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.google.common.collect.Lists;
import lombok.Data;

@Data
public class RbTemplateRoleRsp {

	private String name;

	private Integer roleGroup;

	private List<RbTemplateRoleRsp> children;

	public RbTemplateRoleRsp(Integer code, String name) {
		this.name = name;
		this.roleGroup = code;
		this.children = Lists.newArrayList();
	}

	public RbTemplateRoleRsp(CrRole.RbRoleGroup roleGroup) {
		this(roleGroup, roleGroup.getDesc());
	}

	public RbTemplateRoleRsp(CrRole.RbRoleGroup roleGroup, String name) {
		this(roleGroup.getCode(), name);
	}

}
