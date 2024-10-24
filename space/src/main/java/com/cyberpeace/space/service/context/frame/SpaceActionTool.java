package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceActionTool {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "简述")
	private String description;

	@ApiModelProperty(value = "平台")
	private String platform;
}
