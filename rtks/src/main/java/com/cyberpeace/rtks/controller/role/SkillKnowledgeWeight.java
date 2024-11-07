package com.cyberpeace.rtks.controller.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能知识权重
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SkillKnowledgeWeight implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "编号")
	private String id;

	/**
	 * 重量
	 */
	@ApiModelProperty(value = "权重")
	private Integer weight = 1;

}
