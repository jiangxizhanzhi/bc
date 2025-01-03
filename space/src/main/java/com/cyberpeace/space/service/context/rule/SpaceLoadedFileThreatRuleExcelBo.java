package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceLoadedFileThreatRuleExcelBo {

	@ApiModelProperty(value = "检测类型")
	private String type;

	@ApiModelProperty(value = "内容简介")
	private String description;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "应用工具")
	private String tool;

	@ApiModelProperty(value = "执行方式，自动2，手动1")
	private String executeType;
}
