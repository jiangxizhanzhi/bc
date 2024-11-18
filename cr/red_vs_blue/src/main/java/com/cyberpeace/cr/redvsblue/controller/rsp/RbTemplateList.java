package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;

import lombok.Data;

/**
 * RB 模板列表
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class RbTemplateList {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	private Integer roleGroup;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 角色数据列表
	 *
	 * @see List<RoleUsers>
	 */
	private List<RoleUsers> roleDataList;
}