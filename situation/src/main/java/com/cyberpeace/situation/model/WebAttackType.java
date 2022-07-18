package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebAttackType {

	private String code;

	private String msg;

	@ApiModelProperty("目标节点支持类型：compound: 同时支持节点和区域、element: 只支持节点、area: 只支持区域")
	private String type;

	@ApiModelProperty("源节点是否可选")
	private boolean source;

	@ApiModelProperty("持续时间是否可选")
	private boolean durationTime;

	public WebAttackType(String code, String msg, String type, boolean source,
			boolean durationTime) {
		this.code = code;
		this.msg = msg;
		this.type = type;
		this.source = source;
		this.durationTime = durationTime;
	}
}
