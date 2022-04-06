package com.cyberpeace.cr.hld.constant;

import lombok.Getter;

public enum HldRoleName {

	/**
	 * 角色名称
	 */

	ADMIN("admin", "admin"),
	DIRECTOR("director", "controller"),
	REFEREE("referee", "assessor");

	@Getter
	private String crName;

	@Getter
	private String hldName;

	HldRoleName(String crName, String hldName) {
		this.crName = crName;
		this.hldName = hldName;
	}

}
