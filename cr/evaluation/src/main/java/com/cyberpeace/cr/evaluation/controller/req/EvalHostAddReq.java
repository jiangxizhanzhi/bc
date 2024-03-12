package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalHostAddReq implements Serializable {

	@ApiModelProperty("云模板Id")
	private String cloudTemplateId;

	@ApiModelProperty("规则id")
	private String ruleId;

	@ApiModelProperty("主机id")
	private String hostId;

	@ApiModelProperty("主机名称")
	private String hostName;

	@ApiModelProperty("类型")
	private String type;

	@ApiModelProperty("主机ip")
	private String ip;
}
