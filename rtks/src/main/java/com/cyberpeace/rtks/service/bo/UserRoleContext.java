package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户角色上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class UserRoleContext implements Serializable {

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 水平id
	 */
	private String levelId;
}
