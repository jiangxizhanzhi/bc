package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebAttackType {

	@ApiModelProperty("动效编码")
	private String code;

	@ApiModelProperty("动效名称")
	private String msg;

	@ApiModelProperty("目标节点支持类型：compound: 同时支持节点和区域、element: 只支持节点、area: 只支持区域")
	private String type;

	@ApiModelProperty("源节点是否可选")
	private boolean source;

	@ApiModelProperty("持续时间是否可选")
	private boolean durationTime;

	@ApiModelProperty("漏洞")
	private String leak;

	@ApiModelProperty("工具")
	private String tool;

	public WebAttackType(String code, String msg, String type, boolean source,
			boolean durationTime) {
		this.code = code;
		this.msg = msg;
		this.type = type;
		this.source = source;
		this.durationTime = durationTime;
	}

	public WebAttackType(String code, String msg, String type, boolean source, boolean durationTime, String leak,
						 String tool) {
		this.code = code;
		this.msg = msg;
		this.type = type;
		this.source = source;
		this.durationTime = durationTime;
		this.leak = leak;
		this.tool = tool;
	}
}
