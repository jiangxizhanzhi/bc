package com.cyberpeace.common.model.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DashboardUser implements Serializable {

	/**
	 * 用户登录名
	 */
	@ApiModelProperty("用户登录名")
	private String username;

	/**
	 * 用户展示名
	 */
	@ApiModelProperty("用户展示名")
	private String name;

	/**
	 * 角色
	 */
	@ApiModelProperty("角色")
	private String role;

	/**
	 * 角色类型
	 */
	@ApiModelProperty("角色类型")
	private String roleType;

	/**
	 * 角色码
	 */
	@ApiModelProperty("角色码")
	private Integer roleCode;

	/**
	 * 编号
	 */
	@ApiModelProperty("编号")
	private String id;

	/**
	 * 头像地址
	 */
	@ApiModelProperty("头像地址")
	private String avatar;

	/**
	 * 登陆
	 */
	@ApiModelProperty("登陆")
	private String sign;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态")
	private String status;

	public DashboardUser id(String id) {
		this.id = id;
		return this;
	}

	public DashboardUser status(String status) {
		this.status = status;
		return this;
	}

	public DashboardUser sign(String sign) {
		this.sign = sign;
		return this;
	}

	public DashboardUser roleType(String roleType) {
		this.roleType = roleType;
		return this;
	}

	public DashboardUser role(String role) {
		this.role = role;
		return this;
	}

	public DashboardUser roleCode(Integer roleCode) {
		this.roleCode = roleCode;
		return this;
	}

	public DashboardUser avatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	public DashboardUser username(String username) {
		this.username = username;
		return this;
	}

	public DashboardUser name(String name) {
		this.name = name;
		return this;
	}
}
