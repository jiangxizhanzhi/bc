package com.cyberpeace.cr.crbase.service.context.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionsStatistics {

	@ApiModelProperty(value = "完成比例")
	private Integer finishProportion;

	@ApiModelProperty(value = "未完成比例")
	private Integer notFinishProportion;

	@ApiModelProperty(value = "未提交")
	private Integer notSubmitProportion;

	@ApiModelProperty(value = "未下发")
	private Integer unpublishedProportion;

	@JsonIgnore
	private Integer missionCount;
}
