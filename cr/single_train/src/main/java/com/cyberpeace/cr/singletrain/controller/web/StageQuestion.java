package com.cyberpeace.cr.singletrain.controller.web;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StageQuestion {

	@ApiModelProperty("任务id")
	private String remoteMissionId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("任务描述")
	private String description;

	@ApiModelProperty("训练时间")
	private Date time;

	@ApiModelProperty("训练记录")
	private String record;

	@ApiModelProperty("训练结果")
	private String taskEvaluateStatus;

	@ApiModelProperty("训练结果名称")
	private String taskEvaluateStatusName;
}
