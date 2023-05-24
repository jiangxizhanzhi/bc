package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardGroup implements Serializable {

	@ApiModelProperty("分组名称")
	private String groupname;

	@ApiModelProperty("角色")
	private String role;

	@ApiModelProperty("分组编号")
	private String id;

	@ApiModelProperty("头像")
	private String avatar;

	@ApiModelProperty("组员编号")
	private List<String> members;
	@ApiModelProperty("角色英文")
	private String roleType;
	@ApiModelProperty("角色code")
	private Integer roleCode;

	public DashboardGroup(String role) {
		this.role = role;
		this.avatar = "";
		this.groupname = role;
		this.id = role;
	}

	public DashboardGroup(String role, String roleType, Integer roleCode) {
		this.role = role;
		this.avatar = "";
		this.groupname = role;
		this.id = role;
		this.roleType = roleType;
		this.roleCode = roleCode;
	}
}
