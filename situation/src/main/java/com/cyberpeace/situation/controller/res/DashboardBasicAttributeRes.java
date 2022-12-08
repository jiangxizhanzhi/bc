package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.situation.model.ViewAngle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardBasicAttributeRes implements Serializable {

	@ApiModelProperty("大屏ID")
	private String dashboardId;

	@ApiModelProperty("大屏层级 1-综合态势 2-分靶场态势 3-任务态势")
	private Integer level;

	@ApiModelProperty("大屏标题")
	private String title;

	@ApiModelProperty("大屏副标题")
	private String subTitle;

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("分靶场名称")
	private String subRangeName;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("任务类型")
	private String taskType;

	@ApiModelProperty("科目ID")
	private String subjectId;

	@ApiModelProperty("科目名称")
	private String subjectName;

	@ApiModelProperty("任务/科目开始时间")
	private Date startTime;

	@ApiModelProperty("拓扑ID")
	private String topoId = "demo";

	@ApiModelProperty("刷新时间（s）")
	private Integer refresh = 5;

	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle;

	public DashboardBasicAttributeRes(String dashboardId, Integer level, String title,
			String subTitle, String subRangeId, String taskId, Date startTime,
			ViewAngle viewAngle) {
		this.dashboardId = dashboardId;
		this.level = level;
		this.title = title;
		this.subTitle = subTitle;
		this.subRangeId = subRangeId;
		this.taskId = taskId;
		this.startTime = startTime;
		this.viewAngle = viewAngle;
	}

	public DashboardBasicAttributeRes() {}

}
