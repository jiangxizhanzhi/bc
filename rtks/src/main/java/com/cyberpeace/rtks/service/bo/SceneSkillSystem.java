package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SceneSkillSystem implements Serializable {

	public final static Integer attack_id = 1;// 只传技能关联课程

	public final static Integer defense_id = 2;// 只传知识点关联课程

	@ApiModelProperty(value = "攻击场景对应技能体系id")
	@NotBlank
	private String attackSkillSystemId;

	@ApiModelProperty(value = "防御场景对应技能体系id")
	@NotBlank
	private String defenseSkillSystemId;

}
