package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardGroup implements Serializable {

	/**
	 * 分组名称
	 */
	@ApiModelProperty("分组名称")
	private String groupname;

	/**
	 * 角色
	 */
	@ApiModelProperty("角色")
	private String role;

	/**
	 * 分组编号
	 */
	@ApiModelProperty("分组编号")
	private String id;

	/**
	 * 头像
	 */
	@ApiModelProperty("头像")
	private String avatar;

	/**
	 * 组员编号
	 */
	@ApiModelProperty("组员编号")
	private List<String> members;

	/**
	 * 角色英文
	 */
	@ApiModelProperty("角色英文")
	private String roleType;

	/**
	 * 角色code
	 */
	@ApiModelProperty("角色code")
	private Integer roleCode;

	public DashboardGroup(String role) {
		this.role = role;
		this.avatar = "";
		this.groupname = role;
		this.id = role;
	}

	public DashboardGroup(String role, Integer roleCode, String avatar) {
		this.role = role;
		this.avatar = avatar;
		this.groupname = role;
		this.id = role;
		this.roleCode = roleCode;
	}
}
