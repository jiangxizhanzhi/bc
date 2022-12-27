package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardIdRes implements Serializable {

	@ApiModelProperty("大屏ID")
	private String dashboardId;

	@ApiModelProperty("大屏类型 1-定制 2-模板 3-三方靶标")
	private Integer dashboardType;

	@ApiModelProperty("大屏层级")
	private Integer level;

	@ApiModelProperty("任务类型")
	private String taskType;

	@ApiModelProperty("任务子类型 0-无 1-攻击 2-防御")
	private Integer taskSubType;

	@ApiModelProperty("跳转链接")
	private String thirdUrl;

	public DashboardIdRes() {};

	public DashboardIdRes(String dashboardId, String thirdUrl) {
		this.setDashboardId(dashboardId);
		this.dashboardType = 3;
		this.level = 3;
		this.taskSubType = 0;
		this.thirdUrl = thirdUrl;
	}

	public void setTaskSubType(String taskSubType) {
		this.taskSubType = Integer.valueOf(taskSubType);
	}
}
