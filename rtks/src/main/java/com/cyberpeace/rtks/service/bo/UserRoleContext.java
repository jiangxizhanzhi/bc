package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户岗位上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class UserRoleContext implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 岗位id
	 *
	 * @see String
	 */
	private String roleId;

	/**
	 * 岗位名称
	 *
	 * @see String
	 */
	private String roleName;

	/**
	 * 层级id
	 *
	 * @see String
	 */
	private String levelId;
}
