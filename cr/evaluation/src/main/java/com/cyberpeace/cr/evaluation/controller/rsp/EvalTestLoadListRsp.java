package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalTestLoadListRsp implements Serializable {

	@ApiModelProperty("载荷id")
	private String loadId;

	@ApiModelProperty("载荷名称")
	private String loadName;

	@ApiModelProperty("运行平台")
	private String platform;

	@ApiModelProperty("控制类型: 1=shell，2=web")
	private Integer controlType;
}
