package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SceneSkillSystem implements Serializable {

	public final static Integer ATTACK_ID = 1;

	public final static Integer DEFENSE_ID = 2;

	/**
	 * 攻击场景对应技能体系id
	 */
	@ApiModelProperty(value = "攻击场景对应技能体系id")
	@NotBlank
	private String attackSkillSystemId;

	/**
	 * 防御场景对应技能体系id
	 */
	@ApiModelProperty(value = "防御场景对应技能体系id")
	@NotBlank
	private String defenseSkillSystemId;

}
