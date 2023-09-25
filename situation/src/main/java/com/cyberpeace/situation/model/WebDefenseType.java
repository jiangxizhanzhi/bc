package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebDefenseType {

	@ApiModelProperty("动效编码")
	private String code;

	@ApiModelProperty("动效名称")
	private String msg;

	@ApiModelProperty("工具")
	private String tool;

	public WebDefenseType(String code, String msg, String tool) {
		this.code = code;
		this.msg = msg;
		this.tool = tool;
	}
}
