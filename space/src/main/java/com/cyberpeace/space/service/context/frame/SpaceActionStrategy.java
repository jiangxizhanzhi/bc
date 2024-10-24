package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceActionStrategy {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "归属")
	private String belong;

	@ApiModelProperty(value = "执行脚本")
	private String script;
}
