package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardIdRes implements Serializable {

	@ApiModelProperty("大屏ID")
	private String dashboardId;

	@ApiModelProperty("大屏类型 1-定制大屏 2-自编大屏 3-三方靶标")
	private Integer dashboardType;

	@ApiModelProperty("定制类型（定制大屏用）")
	private Integer customCode;

	@ApiModelProperty("跳转链接（三方靶标用）")
	private String thirdUrl;

	@ApiModelProperty("大屏层级")
	private Integer level;

	@ApiModelProperty("任务类型")
	private String taskType;

	public DashboardIdRes() {};

	public DashboardIdRes(String dashboardId, String thirdUrl) {
		this.setDashboardId(dashboardId);
		this.dashboardType = 3;
		this.customCode = 1;
		this.level = 3;
		this.thirdUrl = thirdUrl;
	}
}
