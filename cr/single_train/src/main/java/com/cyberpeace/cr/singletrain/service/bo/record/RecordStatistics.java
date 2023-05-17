package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;

import com.cyberpeace.common.util.MathUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecordStatistics implements Serializable {

	@ApiModelProperty(value = "未完成数")
	private Integer unfinished;

	@ApiModelProperty(value = "失败数")
	private Integer failed;

	@ApiModelProperty(value = "成功数")
	private Integer successful;

	@ApiModelProperty(value = "未完成百分比")
	private Integer unfinishedPercentage;

	@ApiModelProperty(value = "失败百分比")
	private Integer failedPercentage;

	@ApiModelProperty(value = "成功百分比")
	private Integer successfulPercentage;

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
