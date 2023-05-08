package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件查询请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class EventQueryReq implements Serializable {

	/**
	 * 大屏ID
	 */
	@ApiModelProperty(name = "dashboard_id", value = "大屏ID")
	@NotEmpty
	private String dashboardId;

	/**
	 * 特性ID
	 */
	@ApiModelProperty(name = "feature_id", value = "特征ID")
	private String featureId;

	/**
	 * 分靶场ID
	 */
	@ApiModelProperty(name = "sub_range_id", value = "分靶场ID")
	@NotEmpty
	private String subRangeId;

	/**
	 * 任务ID
	 */
	@ApiModelProperty(name = "task_id", value = "任务ID")
	@NotEmpty
	private String taskId;

	/**
	 * 科目/题目ID
	 */
	@ApiModelProperty(name = "subject_id", value = "科目/题目ID")
	private String subjectId;

	/**
	 * 任务角色
	 */
	@ApiModelProperty("任务角色")
	private String role;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(name = "start_time", value = "开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(name = "end_time", value = "结束时间")
	private Date endTime;

	/**
	 * 设置大屏id
	 *
	 * @param dashboardId 大屏id
	 */
	public void setDashboard_id(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	/**
	 * 设置特征id
	 *
	 * @param featureId 特征id
	 */
	public void setFeature_id(String featureId) {
		this.featureId = featureId;
	}

	/**
	 * 设置分靶场id
	 *
	 * @param subRangeId 分靶场id
	 */
	public void setSub_range_id(String subRangeId) {
		this.subRangeId = subRangeId;
	}

	/**
	 * 设置任务id
	 *
	 * @param taskId 任务id
	 */
	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 设置科目/题目id
	 *
	 * @param subjectId 科目/题目id
	 */
	public void setSubject_id(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * 设置开始时间
	 *
	 * @param startTime 开始时间
	 */
	public void setStart_time(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 设置结束时间
	 *
	 * @param endTime 结束时间
	 */
	public void setEnd_time(Date endTime) {
		this.endTime = endTime;
	}

}
