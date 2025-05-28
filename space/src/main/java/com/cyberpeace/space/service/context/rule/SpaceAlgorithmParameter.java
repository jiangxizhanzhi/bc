package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space 算法参数
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAlgorithmParameter {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "参数名称")
	private String name;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "参数类型")
	private Integer type;

	/**
	 * 默认值
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "默认值")
	private String defaultValue;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "参数说明")
	private String description;
}
