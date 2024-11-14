package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;

import lombok.Data;

/**
 * 红蓝对抗模板列表
 *
 * @author SN
 * @date 2024/11/14
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
	 * 岗位组
	 */
	private Integer roleGroup;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 岗位数据列表
	 */
	private List<RoleUsers> roleDataList;
}