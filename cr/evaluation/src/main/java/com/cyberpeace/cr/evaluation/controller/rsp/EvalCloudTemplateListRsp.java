package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalCloudTemplateListRsp implements Serializable {

	@ApiModelProperty("云模板id")
	private String cloudTemplateId;

	@ApiModelProperty("模板名称")
	private String name;

	@ApiModelProperty("试验目的")
	private String target;

	@ApiModelProperty("试验类型")
	private String type;

	@ApiModelProperty("节点数")
	private Integer nodeCount;

	@ApiModelProperty("创建者")
	private String createUser;

}
