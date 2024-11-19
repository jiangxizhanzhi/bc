package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能权重结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see SkillSimpleRes
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillWeightRes extends SkillSimpleRes implements Serializable {

	/**
	 * 权重
	 *
	 * @see Integer
	 */
	@ApiModelProperty("权重")
	private Integer weight;
}
