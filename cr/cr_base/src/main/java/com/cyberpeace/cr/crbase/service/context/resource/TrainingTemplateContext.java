package com.cyberpeace.cr.crbase.service.context.resource;

import lombok.Data;

/**
 * 培训模板上下文
 * 训练模板中间模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TrainingTemplateContext {

	private String resourceId;

	private String businessTemplateId;

	private String preSkills;

	private String trainSkills;

	private Integer subType;

	private Integer durationTime;

	private String missionsScheme;

	private String roleConfig;

	private String timeLineConfig;

	private Integer peopleNumber;
}
