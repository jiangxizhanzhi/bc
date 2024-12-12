package com.cyberpeace.common.model.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Dashboard 用户
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class DashboardUser implements Serializable {

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户登录名")
	private String username;

	/**
	 * 名称
	 */
	@ApiModelProperty("用户展示名")
	private String name;

	/**
	 * 岗位
	 */
	@ApiModelProperty("角色")
	private String role;

	/**
	 * 岗位类型
	 */
	@ApiModelProperty("角色类型")
	private String roleType;

	/**
	 * 岗位代号
	 */
	@ApiModelProperty("角色码")
	private Integer roleCode;

	/**
	 * id
	 */
	@ApiModelProperty("编号")
	private String id;

	/**
	 * 化身
	 */
	@ApiModelProperty("头像地址")
	private String avatar;

	/**
	 * 标志
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
}
