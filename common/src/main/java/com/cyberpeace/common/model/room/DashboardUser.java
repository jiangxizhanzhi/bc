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

	@ApiModelProperty("编号")
	private String id;

	@ApiModelProperty("头像地址")
	private String avatar;

	@ApiModelProperty("登陆")
	private String sign;

	@ApiModelProperty("状态")
	private String status;
}
