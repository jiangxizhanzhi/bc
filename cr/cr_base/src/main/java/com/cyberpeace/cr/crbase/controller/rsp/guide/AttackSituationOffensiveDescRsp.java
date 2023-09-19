package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttackSituationOffensiveDescRsp implements Serializable {

	@ApiModelProperty(value = "编码")
	private Integer code;

	@ApiModelProperty(value = "类型(攻击/防御)")
	private String name;

	@ApiModelProperty(value = "描述")
	private String desc;

	@ApiModelProperty(value = "成功链接")
	private String successUrl;

	@ApiModelProperty(value = "失败链接")
	private String failUrl;

	@ApiModelProperty("目标节点支持类型：compound: 同时支持节点和区域、element: 只支持节点、area: 只支持区域")
	private String type;

	public AttackSituationOffensiveDescRsp(Integer code, String name, String desc, String successUrl, String failUrl,
										   String type) {
		this.code = code;
		this.name = name;
		this.desc = desc;
		this.successUrl = successUrl;
		this.failUrl = failUrl;
		this.type = type;
	}
}
