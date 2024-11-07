package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import lombok.Data;

/**
 * 角色匹配记录上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoleMatchRecordContext implements Serializable {

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

	/**
	 * 火柴
	 */
	private Integer match;
}
