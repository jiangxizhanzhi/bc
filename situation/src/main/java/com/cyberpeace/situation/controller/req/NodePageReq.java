package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.TimeUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点分页请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class NodePageReq extends PageReqBase implements Serializable {

	/**
	 * 分靶场ID
	 */
	@ApiModelProperty(name = "sub_range_id", value = "分靶场ID")
	private String subRangeId;

	/**
	 * 任务ID
	 */
	@ApiModelProperty(name = "task_id", value = "任务ID")
	private String taskId;

	/**
	 * 科目ID
	 */
	@ApiModelProperty(name = "subject_id", value = "科目ID")
	private String subjectId;

	/**
	 * 角色 红方/白方/蓝方
	 */
	@ApiModelProperty("角色 红方/白方/蓝方")
	private String role;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(name = "start_time", value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(name = "end_time", value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	/**
	 * 节点ID
	 */
	@ApiModelProperty(name = "node_id", value = "节点ID")
	private String nodeId;

	/**
	 * 设置分靶场ID
	 *
	 * @param subRangeId 分靶场ID
	 */
	public void setSub_range_id(String subRangeId) {
		this.subRangeId = subRangeId;
	}

	/**
	 * 设置任务ID
	 *
	 * @param taskId 任务ID
	 */
	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 设置科目ID
	 *
	 * @param subjectId 科目ID
	 */
	public void setSubject_id(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * 设置开始时间
	 *
	 * @param startTime 开始时间
	 */
	public void setStart_time(String startTime) {
		this.startTime = TimeUtil.toDate1(startTime);
	}

	/**
	 * 设置结束时间
	 *
	 * @param endTime 结束时间
	 */
	public void setEnd_time(String endTime) {
		this.endTime = TimeUtil.toDate1(endTime);
	}

	/**
	 * 设置节点ID
	 *
	 * @param nodeId 节点ID
	 */
	public void setNode_id(String nodeId) {
		this.nodeId = nodeId;
	}

}
