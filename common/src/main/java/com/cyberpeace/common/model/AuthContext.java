package com.cyberpeace.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthContext implements Serializable {

	private String organization;

	private String userId;

	private Boolean isWeb;

	private Boolean isSuperAdmin;

	private Boolean isAdmin;

	public Boolean getWeb() {
		return null != isWeb && isWeb;
	}

	public Boolean getSuperAdmin() {
		return null != isSuperAdmin && isSuperAdmin;
	}
}
