package com.cyberpeace.cr.crbase.service.context.resource;

import lombok.Data;

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
