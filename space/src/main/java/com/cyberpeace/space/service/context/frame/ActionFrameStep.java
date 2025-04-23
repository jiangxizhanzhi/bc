package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ActionFrameStep {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "状态，0未下发，1已完成，2已下发未完成")
	private Integer status = 0;

	@ApiModelProperty(value = "行动战法")
	private List<ActionFrameTactic> tactics;
}
