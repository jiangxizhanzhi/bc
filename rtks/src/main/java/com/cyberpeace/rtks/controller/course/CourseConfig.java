package com.cyberpeace.rtks.controller.course;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 用于从数据库中取出课程配置信息
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class CourseConfig {

	/**
	 * 资源id
	 */
	@NotBlank
	String resourceId;
}
