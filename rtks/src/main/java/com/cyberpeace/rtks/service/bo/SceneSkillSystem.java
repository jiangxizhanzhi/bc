package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SceneSkillSystem implements Serializable {

	public static final Integer ATTACK_ID = 1;

	public static final Integer DEFENSE_ID = 2;

	@ApiModelProperty(value = "攻击场景对应技能体系id")
	@NotBlank
	private String attackSkillSystemId;

	@ApiModelProperty(value = "防御场景对应技能体系id")
	@NotBlank
	private String defenseSkillSystemId;

}
