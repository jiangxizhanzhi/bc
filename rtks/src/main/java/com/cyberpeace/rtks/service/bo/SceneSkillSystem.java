package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景技能系统
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel
public class SceneSkillSystem implements Serializable {

	/**
	 * 攻击id
	 */
	public final static Integer ATTACK_ID = 1;

	/**
	 * 防御id
	 */
	public final static Integer DEFENSE_ID = 2;

	/**
	 * 攻击技能系统id
	 */
	@ApiModelProperty(value = "攻击场景对应技能体系id")
	@NotBlank
	private String attackSkillSystemId;

	/**
	 * 防御技能系统id
	 */
	@ApiModelProperty(value = "防御场景对应技能体系id")
	@NotBlank
	private String defenseSkillSystemId;

}
