package com.cyberpeace.cr.crbase.service.context.role;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色查询体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@NoArgsConstructor
public class RoleQuery {

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 岗位群
	 */
	private Integer roleGroup;

	public RoleQuery(String roomId, Integer roleGroup) {
		this.roomId = roomId;
		this.roleGroup = roleGroup;
	}

	public RoleQuery(String roomId, CrRole.RoleGroup roleGroup){
		this(roomId, roleGroup.getCode());
	}
}
