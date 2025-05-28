package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 战法行动
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class TacticAction {

	/**
	 * 编号
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "步骤序号")
	private Integer index;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "步骤名称")
	private String name;

	/**
	 * 内容
	 *
	 * @see String
	 */// @ApiModelProperty(value = "行动类型， 常量type=46")
	// private Integer type;
	@ApiModelProperty(value = "行动描述")
	private String content;
}
