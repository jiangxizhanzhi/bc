package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see SkillResBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillRes extends SkillResBase implements Serializable {

	/**
	 * 技能关联任务
	 *
	 * @see List<MissionRes>
	 */
	@ApiModelProperty("技能关联任务")
	private List<MissionRes> missions;
}