package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillSimpleRes extends SkillResBase implements Serializable {

	/**
	 * 关联任务数
	 */
	@ApiModelProperty("关联任务数")
	private Integer missionCount;
}
