package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 课时练习提交请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class LessonPracticeSubmitReq implements Serializable {

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	@NotBlank
	private String courseId;

	/**
	 * 课时编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	@NotBlank
	private String lessonId;

	/**
	 * 答案提交
	 *
	 * @see List<PracticeSubmitReq>
	 */
	@ApiModelProperty("答案提交")
	private List<PracticeSubmitReq> answers;

}
