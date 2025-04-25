package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexRule {

	@ApiModelProperty(value = "基础数据规则")
	private Integer base = 0;

	@ApiModelProperty(value = "告警字典规则")
	private Integer warning = 0;

	@ApiModelProperty(value = "威胁防御规则")
	private Integer threat = 0;

	@ApiModelProperty(value = "比赛及训练评估规则")
	private Integer race = 0;

	@ApiModelProperty(value = "总数")
	private Integer sum = 0;

	@ApiModelProperty(value = "使用次数")
	private Integer use = 0;

	public void calSum() {
		this.sum = this.base + this.warning + this.race + this.threat;
	}
}
