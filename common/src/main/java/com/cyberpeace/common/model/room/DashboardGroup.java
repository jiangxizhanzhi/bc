package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Dashboard 组
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DashboardGroup implements Serializable {

	/**
	 * 组名
	 *
	 * @see String
	 */
	@ApiModelProperty("分组名称")
	private String groupname;

	/**
	 * 岗位
	 *
	 * @see String
	 */
	@ApiModelProperty("角色")
	private String role;

	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty("分组编号")
	private String id;

	/**
	 * 化身
	 *
	 * @see String
	 */
	@ApiModelProperty("头像")
	private String avatar;

	/**
	 * 成员
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("组员编号")
	private List<String> members;

	/**
	 * 岗位类型
	 *
	 * @see String
	 */
	@ApiModelProperty("角色英文")
	private String roleType;

	/**
	 * 岗位代号
	 *
	 * @see Integer
	 */
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
