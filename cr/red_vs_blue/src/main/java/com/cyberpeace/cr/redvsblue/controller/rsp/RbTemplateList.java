package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;

import lombok.Data;

/**
 * RB 模板列表
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbTemplateList {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 角色组
	 */
	private Integer roleGroup;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 角色数据列表
	 */
	private List<RoleUsers> roleDataList;
}