package com.cyberpeace.rtks.controller.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillKnowledgeWeight implements Serializable {

	@ApiModelProperty(value = "编号")
	private String id;

	@ApiModelProperty(value = "权重")
	private String weight;

}
