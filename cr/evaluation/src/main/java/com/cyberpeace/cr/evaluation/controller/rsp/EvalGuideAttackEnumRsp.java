package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvalGuideAttackEnumRsp implements Serializable {

	@ApiModelProperty("编码")
	private Integer code;

	@ApiModelProperty("类型名称")
	private String name;

	@ApiModelProperty("描述")
	private String description;

	@ApiModelProperty("GIF路径")
	private String url;

	@ApiModelProperty("目标节点支持类型：compound: 同时支持节点和区域、element: 只支持节点、area: 只支持区域")
	private String type;
}
