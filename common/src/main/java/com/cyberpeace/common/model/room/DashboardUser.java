package com.cyberpeace.common.model.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Dashboard 用户
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class DashboardUser implements Serializable {

	@ApiModelProperty("用户登录名")
	private String username;

	@ApiModelProperty("用户展示名")
	private String name;

	@ApiModelProperty("角色")
	private String role;

	@ApiModelProperty("角色类型")
	private String roleType;

	@ApiModelProperty("角色码")
	private Integer roleCode;

	@ApiModelProperty("编号")
	private String id;

	@ApiModelProperty("头像地址")
	private String avatar;

	@ApiModelProperty("登陆")
	private String sign;

	@ApiModelProperty("状态")
	private String status;

	/**
	 * id
	 *
	 * @param id id
	 * @return {@code DashboardUser }
	 */
	public DashboardUser id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * 状态
	 *
	 * @param status 状态
	 * @return {@code DashboardUser }
	 */
	public DashboardUser status(String status) {
		this.status = status;
		return this;
	}

	/**
	 * 标志
	 *
	 * @param sign 标志
	 * @return {@code DashboardUser }
	 */
	public DashboardUser sign(String sign) {
		this.sign = sign;
		return this;
	}

	/**
	 * 角色类型
	 *
	 * @param roleType 角色类型
	 * @return {@code DashboardUser }
	 */
	public DashboardUser roleType(String roleType) {
		this.roleType = roleType;
		return this;
	}

	/**
	 * 角色
	 *
	 * @param role 角色
	 * @return {@code DashboardUser }
	 */
	public DashboardUser role(String role) {
		this.role = role;
		return this;
	}

	/**
	 * 角色代号
	 *
	 * @param roleCode 角色代号
	 * @return {@code DashboardUser }
	 */
	public DashboardUser roleCode(Integer roleCode) {
		this.roleCode = roleCode;
		return this;
	}

	/**
	 * 化身
	 *
	 * @param avatar 化身
	 * @return {@code DashboardUser }
	 */
	public DashboardUser avatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	/**
	 * 用户名
	 *
	 * @param username 用户名
	 * @return {@code DashboardUser }
	 */
	public DashboardUser username(String username) {
		this.username = username;
		return this;
	}
}
