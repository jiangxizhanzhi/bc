package com.cyberpeace.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * auth 上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AuthContext implements Serializable {

	/**
	 * 组织
	 *
	 * @see String
	 */
	private String organization;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 是 Web
	 *
	 * @see Boolean
	 */
	private Boolean isWeb;

	/**
	 * 是超级管理员
	 *
	 * @see Boolean
	 */
	private Boolean isSuperAdmin;

	/**
	 * 是管理员
	 *
	 * @see Boolean
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
