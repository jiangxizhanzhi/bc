package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionReport {

	@ApiModelProperty(value = "任务类别统计")
	private Map<String, Integer> missionsCategory;

	@ApiModelProperty(value = "任务统计")
	private MissionsStatistics missionsStatistics;
}
