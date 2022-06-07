package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ImportLessonReq implements Serializable {

	@ApiModelProperty("课程编号")
	@NotBlank(message = OjBusinessErrorCode.COURSE_INFORMATION_NOT_NULL)
	private String courseId;

	@ApiModelProperty("课时编号列表")
	@NotEmpty(message = OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL)
	private List<String> lessonIds;

}
