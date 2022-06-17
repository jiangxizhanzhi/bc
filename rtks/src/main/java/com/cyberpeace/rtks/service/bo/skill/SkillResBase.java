package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillResBase implements Serializable {

	@ApiModelProperty("技能编号")
	private String resourceId;

	@ApiModelProperty("技能内容")
	private String content;
}
