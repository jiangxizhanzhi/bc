package com.cyberpeace.rtks.controller.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能知识点重量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillKnowledgeWeight implements Serializable {

	/**
	 * 编号
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "编号")
	private String id;

	/**
	 * 权重
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "权重")
	private Integer weight = 1;

}
