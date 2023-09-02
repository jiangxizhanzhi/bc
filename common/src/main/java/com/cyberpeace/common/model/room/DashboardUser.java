package com.cyberpeace.common.model.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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
