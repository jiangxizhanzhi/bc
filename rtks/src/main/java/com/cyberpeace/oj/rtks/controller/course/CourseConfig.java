package com.cyberpeace.oj.rtks.controller.course;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CourseConfig {

	@NotBlank
	String courseId;
}
