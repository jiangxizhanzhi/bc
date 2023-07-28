package com.cyberpeace.cr.crbase.service.context.role;

import com.cyberpeace.common.enums.RoleGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色查询体
 */
@Data
@NoArgsConstructor
public class RoleQuery {

	private String roomId;

	private Integer roleGroup;

	public RoleQuery(String roomId, Integer roleGroup) {
		this.roomId = roomId;
		this.roleGroup = roleGroup;
	}

	public RoleQuery(String roomId, RoleGroup roleGroup){
		this(roomId, roleGroup.getCode());
	}
}
