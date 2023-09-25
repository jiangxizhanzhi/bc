package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebDefenseType {

	@ApiModelProperty("动效编码")
	private String code;

	@ApiModelProperty("动效名称")
	private String msg;

	@ApiModelProperty("漏洞")
	private String leak;

	@ApiModelProperty("工具")
	private String tool;

	public WebDefenseType(String code, String msg, String leak, String tool) {
		this.code = code;
		this.msg = msg;
		this.leak = leak;
		this.tool = tool;
	}
}
