package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import lombok.Data;

@Data
public class BaseRole {

	private String resourceId;

	private String name;

	private Integer roleGroup;

	private String description;

	private List<RoleUsers> roleDataList;
}