package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课练习场景请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class LessonPracticeSceneReq implements Serializable {

	/**
	 * 课id
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 练习id
	 */
	@ApiModelProperty("习题编号")
	private String practiceId;

}
