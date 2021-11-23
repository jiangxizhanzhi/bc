package com.cyberpeace.common.model;

import lombok.Data;

@Data
public class AuthContext {

	private String organization;

	private String likeOrganization;

	private String userId;

	private String likeUser;

	private Boolean isWeb;

	private Boolean isSuperAdmin;

	public Boolean getWeb() {
		return null == isWeb ? false : isWeb;
	}

	public Boolean getSuperAdmin() {
		return null == isSuperAdmin ? false : isSuperAdmin;
	}
}
