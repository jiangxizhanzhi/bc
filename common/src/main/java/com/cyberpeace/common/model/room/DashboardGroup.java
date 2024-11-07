package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dashboard 组
 *
 * @author SN
 * @date 2024/11/07
 */
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

	/**
	 * Dashboard 组
	 *
	 * @param role 角色
	 */
	public DashboardGroup(String role) {
		this.role = role;
		this.avatar = "";
		this.groupname = role;
		this.id = role;
	}

	/**
	 * Dashboard 组
	 *
	 * @param role     角色
	 * @param roleCode 角色代号
	 * @param avatar   化身
	 */
	public DashboardGroup(String role, Integer roleCode, String avatar) {
		this.role = role;
		this.avatar = avatar;
		this.groupname = role;
		this.id = role;
		this.roleCode = roleCode;
	}
}
