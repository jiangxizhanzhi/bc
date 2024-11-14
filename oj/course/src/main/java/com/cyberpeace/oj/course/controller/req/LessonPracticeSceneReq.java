package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课时练习场景请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class LessonPracticeSceneReq implements Serializable {

	/**
	 * 课时编号
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 习题编号
	 */
	@ApiModelProperty("习题编号")
	private String practiceId;

}
