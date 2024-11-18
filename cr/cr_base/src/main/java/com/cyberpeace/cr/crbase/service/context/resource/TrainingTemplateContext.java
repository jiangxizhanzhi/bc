package com.cyberpeace.cr.crbase.service.context.resource;

import lombok.Data;

/**
 * 训练模板中间模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TrainingTemplateContext {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

	/**
	 * 预科技能
	 *
	 * @see String
	 */
	private String preSkills;

	/**
	 * 训练技能
	 *
	 * @see String
	 */
	private String trainSkills;

	/**
	 * 子类型
	 *
	 * @see Integer
	 */
	private Integer subType;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	private Integer durationTime;

	/**
	 * 任务计划
	 *
	 * @see String
	 */
	private String missionsScheme;

	/**
	 * 角色配置
	 *
	 * @see String
	 */
	private String roleConfig;

	/**
	 * 时间线配置
	 *
	 * @see String
	 */
	private String timeLineConfig;

	/**
	 * 人数
	 *
	 * @see Integer
	 */
	private Integer peopleNumber;
}
