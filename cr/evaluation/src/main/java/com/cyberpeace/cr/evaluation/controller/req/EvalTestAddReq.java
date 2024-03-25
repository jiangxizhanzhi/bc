package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalTestAddReq implements Serializable {

	@ApiModelProperty(required = true, value = "云模板id")
	private String cloudTemplateId;

	@ApiModelProperty(required = true, value = "试验名称")
	private String name;

	@ApiModelProperty(required = true, value = "封面图")
	private String logo;

	@ApiModelProperty(required = true, value = "试验类型")
	private Integer type;

	@ApiModelProperty(required = true, value = "人员")
	private List<String> userIds;
}
