package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课实践场景状态请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class LessonPracticeSceneStatusReq implements Serializable {

	/**
	 * 课id
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 实践id
	 */
	@ApiModelProperty("习题编号")
	private String practiceId;

	/**
	 * 状态
	 */
	@ApiModelProperty("场景状态 1=启动 3=暂停")
	private Integer status;

}
