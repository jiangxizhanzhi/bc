package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能简单结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SkillSimpleRes extends SkillResBase implements Serializable {

	/**
	 * 任务计数
	 */
	@ApiModelProperty("关联任务数")
	private Integer missionCount;
}
