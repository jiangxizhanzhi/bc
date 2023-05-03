package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubjectListReq implements Serializable {

	@ApiModelProperty(name = "sub_range_id", value = "分靶场ID")
	private String subRangeId;

	@ApiModelProperty(name = "task_id", value = "任务ID")
	private String taskId;

	@ApiModelProperty(name = "subject_id", value = "科目ID")
	private String subjectId;

	@ApiModelProperty(name = "subject_id", value = "科目ID列表")
	private List<String> subjectIds;

	@ApiModelProperty(name = "task_type", value = "任务类型")
	private String taskType;

	public void setSub_range_id(String subRangeId) {
		this.subRangeId = subRangeId;
	}

	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

	public void setSubject_id(String subjectId) {
		this.subjectId = subjectId;
	}

	public void setTask_type(String taskType) {
		this.taskType = taskType;
	}

	public SubjectListReq() {};

	public SubjectListReq(String subRangeId, String taskId, String subjectId) {
		this.subjectId = subjectId;
		this.subRangeId = subRangeId;
		this.taskId = taskId;
	};

}
