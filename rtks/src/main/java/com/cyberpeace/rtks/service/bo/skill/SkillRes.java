package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillRes extends SkillResBase implements Serializable {

	/**
	 * 技能关联任务
	 */
	@ApiModelProperty("技能关联任务")
	private List<MissionRes> missions;
}