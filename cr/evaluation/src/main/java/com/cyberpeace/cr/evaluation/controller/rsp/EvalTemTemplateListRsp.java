package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalTemTemplateListRsp implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("模板名称")
	private String name;

	@ApiModelProperty("试验目标")
	private String target;

	@ApiModelProperty("试验类型")
	private Integer type;

	@ApiModelProperty("节点数")
	private Integer nodeCount;

	@ApiModelProperty("创建者")
	private String createUser;

	@ApiModelProperty("试验条件")
	private String expCondition;

	@ApiModelProperty("试验方法")
	private String method;

	@ApiModelProperty("阶段数")
	private Integer stageCount;

	@ApiModelProperty("任务数")
	private Integer missionCount;

	@ApiModelProperty("更新时间")
	private String updateTime;
}
