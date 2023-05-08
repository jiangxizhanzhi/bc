package com.cyberpeace.common.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 身份验证上下文
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class AuthContext implements Serializable {

	private String organization;

	private String userId;

	private Boolean isWeb;

	private Boolean isSuperAdmin;

	private Boolean isAdmin;

	/**
	 * 得到网络
	 *
	 * @return {@link Boolean}
	 */
	public Boolean getWeb() {
		return null == isWeb ? false : isWeb;
	}

	/**
	 * 得到超级管理员
	 *
	 * @return {@link Boolean}
	 */
	public Boolean getSuperAdmin() {
		return null == isSuperAdmin ? false : isSuperAdmin;
	}
}
