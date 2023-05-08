package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务列表分页查询请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class TaskListPageReq extends PageReqBase implements Serializable {

	/**
	 * 分靶场ID
	 */
	@ApiModelProperty(name = "sub_range_id", value = "分靶场ID")
	private String subRangeId;

	/**
	 * 责任靶场ID
	 */
	@ApiModelProperty(name = "responsible_range_id", value = "责任靶场ID")
	private String responsibleRangeId;

	/**
	 * 任务id
	 */
	@ApiModelProperty(name = "task_id", value = "任务ID")
	private String taskId;

	/**
	 * 任务类型
	 */
	@ApiModelProperty(name = "task_type", value = "任务类型")
	private String taskType;

	/**
	 * 任务状态
	 */
	@ApiModelProperty(name = "task_status", value = "任务状态")
	private String taskStatus;

	/**
	 * 设置分靶场ID
	 *
	 * @param subRangeId 分靶场ID
	 */
	public void setSub_range_id(String subRangeId) {
		this.subRangeId = subRangeId;
	}

	/**
	 * 设置责任靶场ID
	 *
	 * @param responsibleRangeId 责任靶场ID
	 */
	public void setResponsible_range_id(String responsibleRangeId) {
		this.responsibleRangeId = responsibleRangeId;
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
	 * 设置任务类型
	 *
	 * @param taskType 任务类型
	 */
	public void setTask_type(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * 设置任务状态
	 *
	 * @param taskStatus 任务状态
	 */
	public void setTask_status(String taskStatus) {
		this.taskStatus = taskStatus;
	}

}
