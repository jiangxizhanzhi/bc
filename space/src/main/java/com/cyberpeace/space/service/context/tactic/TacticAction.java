package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TacticAction {

	@ApiModelProperty(value = "步骤序号")
	private Integer index;

	@ApiModelProperty(value = "步骤名称")
	private String name;

	// @ApiModelProperty(value = "行动类型， 常量type=46")
	// private Integer type;
	@ApiModelProperty(value = "行动描述")
	private String content;
}
