package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课时练习场景状态请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class LessonPracticeSceneStatusReq implements Serializable {

	/**
	 * 课时编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 习题编号
	 *
	 * @see String
	 */
	@ApiModelProperty("习题编号")
	private String practiceId;

	/**
	 * 场景状态 1=启动 3=暂停
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景状态 1=启动 3=暂停")
	private Integer status;

}
