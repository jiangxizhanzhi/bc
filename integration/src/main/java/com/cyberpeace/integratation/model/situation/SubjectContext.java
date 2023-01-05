package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubjectContext implements Serializable {

	@ApiModelProperty(value = "科目开始时间")
	private String startTime;

	@ApiModelProperty(value = "科目结束时间")
	private String endTime;

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "任务名称")
	private String taskName;

	@ApiModelProperty(value = "任务状态")
	private String taskStatus;

	@ApiModelProperty(value = "任务类型")
	private String taskType;

	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@ApiModelProperty(value = "科目名称")
	private String subjectName;

	@ApiModelProperty(value = "科目状态")
	private String subjectStatus;

	public void setKeyWord(String keyWord) {
		this.subjectId = keyWord;
	}

	public void setValueWord(String valueWord) {
		this.subjectName = valueWord;
	}

}
