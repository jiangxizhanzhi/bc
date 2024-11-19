package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

/**
 * 技能和使命
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillAndMission implements Serializable {

	/**
	 * 技能id
	 *
	 * @see String
	 */
	private String skillId;

	/**
	 * 任务id
	 *
	 * @see String
	 */
	private String missionId;
}
