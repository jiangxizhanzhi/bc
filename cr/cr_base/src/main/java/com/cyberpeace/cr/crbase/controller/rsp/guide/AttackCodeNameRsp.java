package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttackCodeNameRsp implements Serializable {

	@ApiModelProperty("代号")
	private Integer code;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("目标节点支持类型：compound: 同时支持节点和区域、element: 只支持节点、area: 只支持区域")
	private String type;

	public AttackCodeNameRsp(Integer code, String name, String type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}
}
