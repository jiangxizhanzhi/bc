package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TacticAction {

	private String name;

    @ApiModelProperty(value = "行动类型， 常量type=46")
	private Integer type;

	private String content;
}
