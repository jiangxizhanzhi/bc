package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalMissionOperateContentRsp {

	@ApiModelProperty(value = "操作名称")
	private String operate;

	@ApiModelProperty(value = "操作码")
	private Integer code;


}
