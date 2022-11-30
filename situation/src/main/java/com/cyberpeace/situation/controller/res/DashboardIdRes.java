package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardIdRes implements Serializable {

	@ApiModelProperty("大屏ID")
	private String dashboardId;

	@ApiModelProperty("大屏类型 1-定制 2-模板")
	private Integer dashboardType;

	@ApiModelProperty("任务类型 1-红蓝对抗 2-作战试验")
	private Integer taskType;

	public DashboardIdRes() {};

	public DashboardIdRes(String dashboardId) {
		this.setDashboardId(dashboardId);
		this.dashboardType = 1;
		this.taskType = 1;
	}

}
