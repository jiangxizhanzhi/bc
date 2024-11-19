package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

/**
 * 训练生
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainingRaw implements Serializable {

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

	/**
	 * 训练id
	 *
	 * @see String
	 */
	private String trainingId;

	/**
	 * 训练名称
	 *
	 * @see String
	 */
	private String trainingName;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	private Integer durationTime;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 前置技能
	 *
	 * @see String
	 */
	private String preSkill;

	/**
	 * 训练技能
	 *
	 * @see String
	 */
	private String trainSkill;

	/**
	 * 训练状态
	 *
	 * @see Integer
	 */
	private Integer trainingStatus;

	/**
	 * 图标
	 *
	 * @see String
	 */
	private String logo;

	/**
	 * 训练类型
	 *
	 * @see Integer
	 */
	private Integer trainingType;

	/**
	 * 岗位组
	 *
	 * @see Integer
	 */
	private Integer roleGroup;

}
