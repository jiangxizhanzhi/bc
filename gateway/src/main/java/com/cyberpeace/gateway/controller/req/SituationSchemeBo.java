package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationSchemeBo implements Serializable {

	@ApiModelProperty(value = "分靶场ID")
	private String subRangeId;

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@ApiModelProperty(value = "可视化方案ID")
	private String schemeId;

	@ApiModelProperty(value = "大屏ID")
	private String dashboardId;
}
