package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能结果基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillResBase implements Serializable {

	/**
	 * 技能编号
	 *
	 * @see String
	 */
	@ApiModelProperty("技能编号")
	private String resourceId;

	/**
	 * 技能内容
	 *
	 * @see String
	 */
	@ApiModelProperty("技能内容")
	private String content;
}
