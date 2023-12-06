package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalMissionSchemeTemplateListRsp implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("技战法名称")
	private String name;

	@ApiModelProperty("技战法简述")
	private String description;

	@ApiModelProperty("阶段数")
	private Integer stageCount;

	@ApiModelProperty("目标数")
	private Integer taskCount;
}
