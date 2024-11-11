package com.cyberpeace.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * auth 上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class AuthContext implements Serializable {

	/**
	 * 组织
	 */
	private String organization;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 是 Web
	 */
	private Boolean isWeb;

	/**
	 * 是超级管理员
	 */
	private Boolean isSuperAdmin;

	/**
	 * 是管理员
	 */
	private Boolean isAdmin;

	/**
	 * 获取Web
	 *
	 * @return {@code Boolean }
	 */
	public Boolean getWeb() {
		return null == isWeb ? false : isWeb;
	}

	/**
	 * 获取超管理
	 *
	 * @return {@code Boolean }
	 */
	public Boolean getSuperAdmin() {
		return null == isSuperAdmin ? false : isSuperAdmin;
	}
}
