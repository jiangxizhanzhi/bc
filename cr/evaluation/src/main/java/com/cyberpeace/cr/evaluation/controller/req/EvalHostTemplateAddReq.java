package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalHostTemplateAddReq implements Serializable {

	@ApiModelProperty("主机id")
	private String hostId;

	@ApiModelProperty("主机名称")
	private String hostName;

	@ApiModelProperty("类型")
	private String type;

	@ApiModelProperty("主机ip")
	private String ip;
}
