package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TacticTool {

	private String name;
	@ApiModelProperty(value = "行动类型， 常量type=47")
	private Integer type;

	private String content;
}
