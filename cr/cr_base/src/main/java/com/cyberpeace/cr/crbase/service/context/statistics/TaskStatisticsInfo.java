package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务统计信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TaskStatisticsInfo implements Serializable {

	/**
	 * 成功任务计数
	 */
	@ApiModelProperty(value = "成功数")
	private Integer successMissionsCount;

	/**
	 * 失败任务计数
	 */
	@ApiModelProperty(value = "失败数")
	private Integer failMissionsCount;

	/**
	 * 拒绝任务计数
	 */
	@ApiModelProperty(value = "驳回数")
	private Integer rejectMissionsCount;

	/**
	 * 提交任务计数
	 */
	@ApiModelProperty(value = "上报任务")
	private Integer submitMissionCount;

	/**
	 * 等任务计数
	 */
	@ApiModelProperty(value = "待评估数")
	private Integer waitMissionsCount;
}
