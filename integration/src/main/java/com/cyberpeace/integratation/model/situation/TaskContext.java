package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.cyberpeace.integratation.constant.TaskMode;
import com.cyberpeace.integratation.constant.TaskStatus;
import com.cyberpeace.integratation.constant.TaskType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskContext implements Serializable {

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "任务名称")
	private String taskName;

	@ApiModelProperty(value = "任务状态")
	private String taskStatus;

	@ApiModelProperty(value = "任务类型")
	private String taskType;

	@ApiModelProperty(value = "任务模式")
	private String taskMode;

	@ApiModelProperty(value = "责任靶场名称")
	private String responsibleRangeName;

	@ApiModelProperty(value = "任务开始时间")
	private String time;

	@ApiModelProperty(value = "科目列表")
	private List<SubjectContext> subjectList;

	public void setTaskType(String taskType) {
		this.taskType = Arrays.stream(TaskType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(taskType)).findFirst().orElse(TaskType.QB)
				.getDesc();
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = Arrays.stream(TaskStatus.class.getEnumConstants())
				.filter(t -> t.getCode().equals(taskStatus)).findFirst().orElse(TaskStatus.QT)
				.getName();
	}

	public void setTaskMode(String taskMode) {
		this.taskMode = Arrays.stream(TaskMode.class.getEnumConstants())
				.filter(t -> t.getCode().equals(taskMode)).findFirst().orElse(TaskMode.QT)
				.getName();
	}
}
