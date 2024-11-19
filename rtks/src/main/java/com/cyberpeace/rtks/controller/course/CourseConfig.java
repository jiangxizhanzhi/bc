package com.cyberpeace.rtks.controller.course;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 用于从数据库中取出课程配置信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class CourseConfig {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@NotBlank
	String resourceId;
}
