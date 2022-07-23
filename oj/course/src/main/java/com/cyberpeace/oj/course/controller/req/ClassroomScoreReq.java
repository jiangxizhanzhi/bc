package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ClassroomScoreReq implements Serializable {

	@ApiModelProperty("课程表编号")
	@NotNull
	private String classroomId;

	@ApiModelProperty("任务Id")
	@NotNull
	private String taskId;

}
