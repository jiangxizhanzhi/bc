package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class LessonPracticeSceneStatusReq implements Serializable {

	@ApiModelProperty("课时编号")
	private String lessonId;

	@ApiModelProperty("习题编号")
	private String practiceId;

	@ApiModelProperty("场景状态 1=启动 3=暂停")
	private Integer status;

}
