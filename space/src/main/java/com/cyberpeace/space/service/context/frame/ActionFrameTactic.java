package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionFrameTactic {

	private String resourceId;

	private String name;

    @ApiModelProperty(value = "战法类型，1攻击2防御")
	private Integer type;
}
