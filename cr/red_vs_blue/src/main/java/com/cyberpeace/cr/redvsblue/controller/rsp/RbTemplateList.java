package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;

import lombok.Data;

@Data
public class RbTemplateList {

	private String resourceId;

	private String name;

	private Integer roleGroup;

	private String description;

	private List<RoleUsers> roleDataList;
}