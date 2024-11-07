package com.cyberpeace.cr.crbase.service.context.resource;

import lombok.Data;

/**
 * 训练模板中间模型
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainingTemplateContext {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

	/**
	 * 预科技能
	 */
	private String preSkills;

	/**
	 * 训练技能
	 */
	private String trainSkills;

	/**
	 * 子类型
	 */
	private Integer subType;

	/**
	 * 持续时间
	 */
	private Integer durationTime;

	/**
	 * 任务计划
	 */
	private String missionsScheme;

	/**
	 * 角色配置
	 */
	private String roleConfig;

	/**
	 * 时间线配置
	 */
	private String timeLineConfig;

	/**
	 * 人数
	 */
	private Integer peopleNumber;
}
