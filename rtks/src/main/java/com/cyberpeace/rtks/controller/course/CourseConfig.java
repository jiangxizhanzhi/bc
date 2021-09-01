package com.cyberpeace.rtks.controller.course;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 用于从数据库中取出课程配置信息
 * */
@Data
public class CourseConfig {

	@NotBlank
	String resourceId;
}
