package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

/**
 * 角色技能结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoleSkillRes implements Serializable {

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 技能计数
	 */
	private Integer skillCount;

	/**
	 * 任务计数
	 */
	private Integer missionCount;
}
