package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SkillRes extends SkillResBase implements Serializable {

	/**
	 * 任务
	 */
	@ApiModelProperty("技能关联任务")
	private List<MissionRes> missions;
}