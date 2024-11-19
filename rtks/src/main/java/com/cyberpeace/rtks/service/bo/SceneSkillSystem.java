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
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class SceneSkillSystem implements Serializable {

	/**
	 * 攻击id
	 *
	 * @see Integer
	 */
	public final static Integer ATTACK_ID = 1;

	/**
	 * 防御id
	 *
	 * @see Integer
	 */
	public final static Integer DEFENSE_ID = 2;

	/**
	 * 攻击场景对应技能体系id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "攻击场景对应技能体系id")
	@NotBlank
	private String attackSkillSystemId;

	/**
	 * 防御场景对应技能体系id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "防御场景对应技能体系id")
	@NotBlank
	private String defenseSkillSystemId;

}
