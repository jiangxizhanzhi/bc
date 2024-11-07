package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

/**
 * 技能和使命
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SkillAndMission implements Serializable {

	/**
	 * 技能id
	 */
	private String skillId;

	/**
	 * 任务id
	 */
	private String missionId;
}
