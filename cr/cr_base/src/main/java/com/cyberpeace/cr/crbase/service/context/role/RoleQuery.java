package com.cyberpeace.cr.crbase.service.context.role;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色查询体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class RoleQuery {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 角色组
	 *
	 * @see Integer
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
