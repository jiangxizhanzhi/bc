package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRuleRsp {

	@ApiModelProperty("resourceId")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("描述")
	private String description;

	@ApiModelProperty("得分")
	private Integer score;

	@ApiModelProperty("房间号")
	private String rootId;
}
