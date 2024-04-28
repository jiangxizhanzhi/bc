package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalHostTemplateAddReq implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty(value = "主机id", required = true)
	private String hostId;

	@ApiModelProperty(value = "主机名称", required = true)
	private String hostName;

	@ApiModelProperty(value = "类型", required = true)
	private String type;

	@ApiModelProperty("主机ip")
	private String ip;
}
