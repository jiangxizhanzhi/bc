package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskStatisticsInfo implements Serializable {

	@ApiModelProperty(value = "成功数")
	private Integer successMissionsCount;

	@ApiModelProperty(value = "失败数")
	private Integer failMissionsCount;

	@ApiModelProperty(value = "驳回数")
	private Integer rejectMissionsCount;

	@ApiModelProperty(value = "上报任务")
	private Integer submitMissionCount;

	@ApiModelProperty(value = "待评估数")
	private Integer waitMissionsCount;
}
