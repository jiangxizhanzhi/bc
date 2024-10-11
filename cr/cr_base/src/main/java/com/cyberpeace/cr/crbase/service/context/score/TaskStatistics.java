package com.cyberpeace.cr.crbase.service.context.score;

import com.cyberpeace.common.util.MathUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务统计
 *
 * @author SN
 * @date 2023/05/23
 */
@Data
public class TaskStatistics {

	@ApiModelProperty(value = "未评估数")
	private Integer notEvaluated;

	@ApiModelProperty(value = "未评估百分比")
	private Integer notEvaluatedPercentage;

	@ApiModelProperty(value = "未完成数")
	private Integer unfinished;

	@ApiModelProperty(value = "未完成百分比")
	private Integer unfinishedPercentage;

	@ApiModelProperty(value = "失败数")
	private Integer failed;

	@ApiModelProperty(value = "失败百分比")
	private Integer failedPercentage;

	@ApiModelProperty(value = "成功数")
	private Integer successful;

	@ApiModelProperty(value = "成功百分比")
	private Integer successfulPercentage;

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
