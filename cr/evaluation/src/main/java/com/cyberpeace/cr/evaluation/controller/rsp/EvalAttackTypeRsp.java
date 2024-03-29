package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvalAttackTypeRsp implements Serializable {

	@ApiModelProperty("代号")
	private Integer code;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("图片路径")
	private String url;

	@ApiModelProperty("类型")
	private String type;
}
