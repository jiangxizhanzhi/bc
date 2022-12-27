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

	@ApiModelProperty("大屏层级")
	private Integer level;

	@ApiModelProperty("任务类型")
	private String taskType;

	@ApiModelProperty("任务子类型 1-攻击 2-防御")
	private Integer taskSubType;

	@ApiModelProperty("大屏标题")
	private String title;

	@ApiModelProperty("大屏副标题")
	private String subTitle;

	public DashboardIdRes() {};

	public DashboardIdRes(String dashboardId, String taskType) {
		this.setDashboardId(dashboardId);
		this.dashboardType = 1;
		this.taskType = taskType;
		this.taskSubType = 1;
		this.title = "XXX任务态势";
		this.subTitle = "";

	}

}
