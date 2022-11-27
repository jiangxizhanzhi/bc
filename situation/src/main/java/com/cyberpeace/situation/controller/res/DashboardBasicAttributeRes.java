package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardBasicAttributeRes implements Serializable {

	@ApiModelProperty("大屏ID")
	private String dashboardId;

	@ApiModelProperty("大屏标题")
	private String title;

	@ApiModelProperty("大屏层级")
	private Integer level;

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("分靶场名称")
	private String subRangeName;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("科目ID")
	private String subjectId;

	@ApiModelProperty("科目名称")
	private String subjectName;

	@ApiModelProperty("任务/科目开始时间")
	private Date startTime;

	@ApiModelProperty("刷新时间（s）")
	private Integer refresh;

	public DashboardBasicAttributeRes() {
		this.level = 1;
		this.title = "LG中心综合态势";
		this.subRangeId = "zhxfbc";
		this.subRangeName = "ZH性分靶场";
		this.taskId = "task_001";
		this.taskName = "任务001";
		this.subjectId = "subject_001";
		this.subjectName = "科目001";
		this.startTime = new Date();
		this.refresh = 5;
	}

}
