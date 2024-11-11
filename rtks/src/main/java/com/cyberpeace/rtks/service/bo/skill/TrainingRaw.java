package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

/**
 * 训练生
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainingRaw implements Serializable {

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

	/**
	 * 训练id
	 */
	private String trainingId;

	/**
	 * 训练名称
	 */
	private String trainingName;

	/**
	 * 持续时间
	 */
	private Integer durationTime;

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 预技能
	 */
	private String preSkill;

	/**
	 * 训练技能
	 */
	private String trainSkill;

	/**
	 * 训练状态
	 */
	private Integer trainingStatus;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 训练类型
	 */
	private Integer trainingType;

	/**
	 * 角色组
	 */
	private Integer roleGroup;

//	private Boolean available;
}
