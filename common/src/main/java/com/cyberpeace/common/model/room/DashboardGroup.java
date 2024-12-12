package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Dashboard 组
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class DashboardGroup implements Serializable {

	/**
	 * 组名
	 */
	@ApiModelProperty("分组名称")
	private String groupname;

	/**
	 * 岗位
	 */
	@ApiModelProperty("角色")
	private String role;

	/**
	 * id
	 */
	@ApiModelProperty("分组编号")
	private String id;

	/**
	 * 化身
	 */
	@ApiModelProperty("头像")
	private String avatar;

	/**
	 * 成员
	 */
	@ApiModelProperty("组员编号")
	private List<String> members;

	/**
	 * 岗位类型
	 */
	@ApiModelProperty("角色英文")
	private String roleType;

	/**
	 * 岗位代号
	 */
	@ApiModelProperty("角色code")
	private Integer roleCode;

	/**
	 * Dashboard 组
	 *
	 * @param role 岗位
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
	 * @param role     岗位
	 * @param roleCode 岗位代号
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
