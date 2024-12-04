package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导入课请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ImportLessonReq implements Serializable {

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	@NotBlank(message = OjBusinessErrorCode.COURSE_INFORMATION_NOT_NULL)
	private String courseId;

	/**
	 * 课id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("课时编号列表")
	@NotEmpty(message = OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL)
	private List<String> lessonIds;

}
