package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class LessonPracticeSubmitReq implements Serializable {

	@ApiModelProperty("课程编号")
	@NotBlank
	private String courseId;

	@ApiModelProperty("课时编号")
	@NotBlank
	private String lessonId;

	@ApiModelProperty("提交时间")
	@NotBlank
	private String nowKnowledgeTime;

	@ApiModelProperty("答案提交")
	private List<PracticeSubmitReq> answers;

}
