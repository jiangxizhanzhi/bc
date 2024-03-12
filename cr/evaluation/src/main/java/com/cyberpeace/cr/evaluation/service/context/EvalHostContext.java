package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalHostContext implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("主机名称")
	private String hostName;

	@ApiModelProperty("主机id")
	private String hostId;

	@ApiModelProperty("类型")
	private String type;

	@ApiModelProperty("ip地址")
	private String ip;
}
