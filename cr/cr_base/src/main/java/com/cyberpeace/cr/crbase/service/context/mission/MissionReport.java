package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务统计报告
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class MissionReport {

	/**
	 * 任务类别
	 *
	 * @see Map<String, Integer>
	 */
	@ApiModelProperty(value = "任务类别统计")
	private Map<String, Integer> missionsCategory;

	/**
	 * 任务统计
	 *
	 * @see MissionsStatistics
	 */
	@ApiModelProperty(value = "任务统计")
	private MissionsStatistics missionsStatistics;
}
