package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebAttackType {

	/**
	 * 动效编码
	 */
	@ApiModelProperty("动效编码")
	private String code;

	/**
	 * 动效名称
	 */
	@ApiModelProperty("动效名称")
	private String msg;

	/**
	 * 目标节点支持类型：compound: 同时支持节点和区域、element: 只支持节点、area: 只支持区域
	 */
	@ApiModelProperty("目标节点支持类型：compound: 同时支持节点和区域、element: 只支持节点、area: 只支持区域")
	private String type;

	/**
	 * 源节点是否可选
	 */
	@ApiModelProperty("源节点是否可选")
	private boolean source;

	/**
	 * 持续时间是否可选
	 */
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
