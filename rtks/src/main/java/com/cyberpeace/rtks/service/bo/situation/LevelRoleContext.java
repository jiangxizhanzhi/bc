package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import lombok.Data;

/**
 * 级别角色上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class LevelRoleContext implements Serializable {

	/**
	 * 水平id
	 */
	private String levelId;

	/**
	 * 水平名称
	 */
	private String levelName;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

}
