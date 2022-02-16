package com.cyberpeace.cr.crbase.service.context.role;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleQuery {

	private String roomId;

	private Integer roleGroup;

	public RoleQuery(String roomId, Integer roleGroup) {
		this.roomId = roomId;
		this.roleGroup = roleGroup;
	}

	public RoleQuery(String roomId, CrRole.RoleGroup roleGroup){
		this(roomId, roleGroup.getCode());
	}
}
