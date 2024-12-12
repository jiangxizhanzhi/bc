package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课实践场景状态请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class LessonPracticeSceneStatusReq implements Serializable {

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 实践id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题编号")
	private String practiceId;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景状态 1=启动 3=暂停")
	private Integer status;

}
