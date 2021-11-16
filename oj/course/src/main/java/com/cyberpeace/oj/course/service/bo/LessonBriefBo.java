package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class LessonBriefBo implements Serializable {

	@ApiModelProperty("课程编号")
	private String courseId;

	@ApiModelProperty("课程名称")
	private String courseName;

	@ApiModelProperty("课时编号")
	private String lessonId;

	@ApiModelProperty("课时名称")
	private String lessonName;

}
