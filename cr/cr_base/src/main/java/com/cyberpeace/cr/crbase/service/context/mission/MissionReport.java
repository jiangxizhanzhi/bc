package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务统计报告
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionReport {

	@ApiModelProperty(value = "任务类别统计")
	private Map<String, Integer> missionsCategory;

	@ApiModelProperty(value = "任务统计")
	private MissionsStatistics missionsStatistics;
}
