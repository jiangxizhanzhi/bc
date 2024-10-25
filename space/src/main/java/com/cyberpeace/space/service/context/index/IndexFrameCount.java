package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexFrameCount {

	@ApiModelProperty(value = "攻击流程的JW防御行动框架")
	private Integer process;

	@ApiModelProperty(value = "攻击目标的JW防御行动框架")
	private Integer target;

	private Integer sum;

	public void calSum() {
		this.sum = this.process + this.target;
	}
}
