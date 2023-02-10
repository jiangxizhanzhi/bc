package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationSchemeBo implements Serializable {

	@JsonProperty("subRangeId")
	@ApiModelProperty(value = "分靶场ID")
	private String subRangeId;

	@JsonProperty("taskId")
	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@JsonProperty("subjectId")
	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@JsonProperty("schemeId")
	@ApiModelProperty(value = "可视化方案ID")
	private String schemeId;

	@JsonProperty("dashboardId")
	@ApiModelProperty(value = "大屏ID")
	private String dashboardId;
}
