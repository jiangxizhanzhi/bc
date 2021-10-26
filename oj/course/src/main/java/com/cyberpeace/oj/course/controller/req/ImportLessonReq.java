package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class ImportLessonReq implements Serializable {

	@ApiModelProperty("课程编号")
	@NotBlank
	private String courseId;

	@ApiModelProperty("课时编号列表")
	@NotEmpty
	private List<String> lessonIds;

}
