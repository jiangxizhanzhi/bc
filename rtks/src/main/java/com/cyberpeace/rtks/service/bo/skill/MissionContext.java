package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务背景
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionContext implements Serializable {

	/**
	 * 任务模板id
	 *
	 * @see String
	 */
	private String missionTemplateId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

	/**
	 * 技能id
	 *
	 * @see String
	 */
	private String skillId;

	/**
	 * 技能内容
	 *
	 * @see String
	 */
	private String skillContent;
}
