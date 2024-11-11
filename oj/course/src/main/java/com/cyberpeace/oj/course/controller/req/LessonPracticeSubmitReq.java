package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 课练习提交请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class LessonPracticeSubmitReq implements Serializable {

	/**
	 * 课程id
	 */
	@ApiModelProperty("课程编号")
	@NotBlank
	private String courseId;

	/**
	 * 课id
	 */
	@ApiModelProperty("课时编号")
	@NotBlank
	private String lessonId;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案提交")
	private List<PracticeSubmitReq> answers;

}
