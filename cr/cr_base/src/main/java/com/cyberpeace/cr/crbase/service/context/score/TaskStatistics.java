package com.cyberpeace.cr.crbase.service.context.score;

import com.cyberpeace.common.util.MathUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务统计
 *
 * @author SN
 * @date 2023/05/23
 */
@Getter
@Setter
public class TaskStatistics {

	/**
	 * 未评估
	 */
	@ApiModelProperty(value = "未评估数")
	private Integer notEvaluated;

	/**
	 * 未评估百分比
	 */
	@ApiModelProperty(value = "未评估百分比")
	private Integer notEvaluatedPercentage;

	/**
	 * 未完成
	 */
	@ApiModelProperty(value = "未完成数")
	private Integer unfinished;

	/**
	 * 未完成百分比
	 */
	@ApiModelProperty(value = "未完成百分比")
	private Integer unfinishedPercentage;

	/**
	 * 失败
	 */
	@ApiModelProperty(value = "失败数")
	private Integer failed;

	/**
	 * 失败百分比
	 */
	@ApiModelProperty(value = "失败百分比")
	private Integer failedPercentage;

	/**
	 * 成功
	 */
	@ApiModelProperty(value = "成功数")
	private Integer successful;

	/**
	 * 成功百分比
	 */
	@ApiModelProperty(value = "成功百分比")
	private Integer successfulPercentage;

	/**
	 * 任务计数
	 */
	@ApiModelProperty(value = "任务总数")
	private Integer missionCount;

	/**
	 * 计算
	 * 驳回算未完成
	 */
	public void calculate() {
		if (this.successful == null || this.failed == null || this.missionCount == null
				|| this.notEvaluated == null) {
			return;
		}
		this.unfinished = this.missionCount - this.failed - this.successful - this.notEvaluated;
		this.unfinishedPercentage = MathUtils.roundDivide(this.unfinished * 100, this.missionCount);
		this.successfulPercentage = MathUtils.roundDivide(this.successful * 100, this.missionCount);
		this.failedPercentage = MathUtils.roundDivide(this.failed * 100, this.missionCount);
		this.notEvaluatedPercentage = MathUtils.roundDivide(this.failed * 100, this.missionCount);
	}
}
