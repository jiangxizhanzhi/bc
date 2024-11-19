package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能简单结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see SkillResBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillSimpleRes extends SkillResBase implements Serializable {

	/**
	 * 关联任务数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("关联任务数")
	private Integer missionCount;
}
