package com.cyberpeace.common.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
public class AuthContext implements Serializable {

	private String organization;

	private String userId;

	private Boolean isWeb;

	private Boolean isSuperAdmin;

	private Boolean isAdmin;

	private String haiboToken;

	public boolean isHaiboUser() {
		return StringUtils.isNotEmpty(haiboToken);
	}

	public Boolean getWeb() {
		return null == isWeb ? false : isWeb;
	}

	public Boolean getSuperAdmin() {
		return null == isSuperAdmin ? false : isSuperAdmin;
	}
}
