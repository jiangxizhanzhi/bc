package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能重量结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SkillWeightRes extends SkillSimpleRes implements Serializable {

	/**
	 * 重量
	 */
	@ApiModelProperty("权重")
	private Integer weight;
}
