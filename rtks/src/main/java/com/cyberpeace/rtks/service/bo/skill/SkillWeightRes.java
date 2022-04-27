package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillWeightRes extends SkillSimpleRes implements Serializable {

	@ApiModelProperty("权重")
	private Integer weight;
}
