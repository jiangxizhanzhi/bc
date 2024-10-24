package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceActionStrategy {
	@ApiModelProperty(value = "新增、修改，前端只用传id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "归属")
	private String route;

	@ApiModelProperty(value = "执行脚本")
	private String script;
}
