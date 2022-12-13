package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.TimeUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodePageReq extends PageReqBase implements Serializable {

	@ApiModelProperty(name = "sub_range_id", value = "分靶场ID")
	private String subRangeId;

	@ApiModelProperty(name = "task_id", value = "任务ID")
	private String taskId;

	@ApiModelProperty(name = "subject_id", value = "科目ID")
	private String subjectId;

	@ApiModelProperty("角色 红方/白方/蓝方")
	private String role;

	@ApiModelProperty(name = "start_time", value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@ApiModelProperty(name = "end_time", value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@ApiModelProperty(name = "node_id", value = "节点ID")
	private String nodeId;

	public void setSub_range_id(String subRangeId) {
		this.subRangeId = subRangeId;
	}

	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

	public void setSubject_id(String subjectId) {
		this.subjectId = subjectId;
	}

	public void setStart_time(String startTime) {
		this.startTime = TimeUtil.toDate1(startTime);
	}

	public void setEnd_time(String endTime) {
		this.endTime = TimeUtil.toDate1(endTime);
	}

	public void setNode_id(String nodeId) {
		this.nodeId = nodeId;
	}

}
