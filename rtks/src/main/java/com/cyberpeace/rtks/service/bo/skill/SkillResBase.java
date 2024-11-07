package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能结果基础
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SkillResBase implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("技能编号")
	private String resourceId;

	/**
	 * 内容
	 */
	@ApiModelProperty("技能内容")
	private String content;
}
