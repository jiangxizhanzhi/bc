package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class RbTemplateRoleRsp {

	private String name;

	private Integer roleGroup;

	private List<RbTemplateRoleRsp> children;

	public RbTemplateRoleRsp(String role, Integer code) {
		this.name = role;
		this.roleGroup = code;
		this.children = Lists.newArrayList();
	}
}
