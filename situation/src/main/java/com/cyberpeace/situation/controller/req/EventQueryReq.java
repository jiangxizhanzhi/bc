package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventQueryReq implements Serializable {

	@ApiModelProperty(name = "dashboard_id", value = "大屏ID")
	@NotEmpty
	private String dashboardId;

	@ApiModelProperty(name = "feature_id", value = "特征ID")
	private String featureId;

	@ApiModelProperty(name = "sub_range_id", value = "分靶场ID")
	@NotEmpty
	private String subRangeId;

	@ApiModelProperty(name = "task_id", value = "任务ID")
	@NotEmpty
	private String taskId;

	@ApiModelProperty(name = "subject_id", value = "科目/题目ID")
	private String subjectId;

	@ApiModelProperty("任务角色")
	private String role;

	@ApiModelProperty(name = "start_time", value = "开始时间")
	private Date startTime;

	@ApiModelProperty(name = "end_time", value = "结束时间")
	private Date endTime;

	public void setDashboard_id(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public void setFeature_id(String featureId) {
		this.featureId = featureId;
	}

	public void setSub_range_id(String subRangeId) {
		this.subRangeId = subRangeId;
	}

	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

	public void setSubject_id(String subjectId) {
		this.subjectId = subjectId;
	}

	public void setStart_time(Date startTime) {
		this.startTime = startTime;
	}

	public void setEnd_time(Date endTime) {
		this.endTime = endTime;
	}

}
