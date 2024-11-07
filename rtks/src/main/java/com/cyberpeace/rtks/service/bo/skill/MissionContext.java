package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务背景
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionContext implements Serializable {

	/**
	 * 任务模板id
	 */
	private String missionTemplateId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

	/**
	 * 技能id
	 */
	private String skillId;

	/**
	 * 技能内容
	 */
	private String skillContent;
}
