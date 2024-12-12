package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务统计报告
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionReport {

	/**
	 * 任务类别
	 */
	@ApiModelProperty(value = "任务类别统计")
	private Map<String, Integer> missionsCategory;

	/**
	 * 任务统计
	 */
	@ApiModelProperty(value = "任务统计")
	private MissionsStatistics missionsStatistics;
}
