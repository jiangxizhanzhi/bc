package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程简介 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class LessonBriefBo implements Serializable {

	/**
	 * 课程id
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 课id
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 课名称
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

}
