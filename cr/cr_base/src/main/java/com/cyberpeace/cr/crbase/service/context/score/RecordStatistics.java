package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;

import com.cyberpeace.common.util.MathUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 记录统计信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class RecordStatistics implements Serializable {

	/**
	 * 未完成
	 */
	@ApiModelProperty(value = "未完成数")
	private Integer unfinished;

	/**
	 * 失败
	 */
	@ApiModelProperty(value = "失败数")
	private Integer failed;

	/**
	 * 成功
	 */
	@ApiModelProperty(value = "成功数")
	private Integer successful;

	/**
	 * 未完成百分比
	 */
	@ApiModelProperty(value = "未完成百分比")
	private Integer unfinishedPercentage;

	/**
	 * 失败百分比
	 */
	@ApiModelProperty(value = "失败百分比")
	private Integer failedPercentage;

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

	public void calculate() {
		if (this.successful == null || this.failed == null || this.missionCount == null) {
			return;
		}
		this.unfinished = this.missionCount - this.failed - this.successful;
		this.unfinishedPercentage = MathUtils.roundDivide(this.unfinished * 100, this.missionCount);
		this.successfulPercentage = MathUtils.roundDivide(this.successful * 100, this.missionCount);
		this.failedPercentage = MathUtils.roundDivide(this.failed * 100, this.missionCount);

	}
}
