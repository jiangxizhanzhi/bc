package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexStrategy {

	@ApiModelProperty(value = "操作系统")
	private Integer os = 0;

	@ApiModelProperty(value = "数据库")
	private Integer database = 0;

	@ApiModelProperty(value = "Web中间件")
	private Integer middleware = 0;

	@ApiModelProperty(value = "交换机")
	private Integer switchboard = 0;

	@ApiModelProperty(value = "路由器")
	private Integer router = 0;

	@ApiModelProperty(value = "大数据平台")
	private Integer bigData = 0;

	@ApiModelProperty(value = "防病毒系统")
	private Integer antiVirus = 0;

	@ApiModelProperty(value = "终端安全系统")
	private Integer terminal = 0;

	@ApiModelProperty(value = "边界防护设备")
	private Integer border = 0;

	@ApiModelProperty(value = "入侵检测系统:")
	private Integer invasionCheck = 0;

	@ApiModelProperty(value = "策略库使用次数")
	private Integer use = 0;

	@ApiModelProperty(value = "总数")
	private Integer sum = 0;

	public void calSum() {
		this.sum = this.os + this.database + this.middleware + this.switchboard + this.router
				+ this.bigData + this.antiVirus + this.terminal + this.border + this.invasionCheck;
	}
}
