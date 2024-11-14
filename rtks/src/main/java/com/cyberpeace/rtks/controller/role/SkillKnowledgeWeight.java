package com.cyberpeace.rtks.controller.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillKnowledgeWeight implements Serializable {

	/**
	 * 编号
	 */
	@ApiModelProperty(value = "编号")
	private String id;

	/**
	 * 权重
	 */
	@ApiModelProperty(value = "权重")
	private Integer weight = 1;

}
