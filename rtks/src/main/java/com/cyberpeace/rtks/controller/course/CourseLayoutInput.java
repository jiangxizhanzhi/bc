package com.cyberpeace.rtks.controller.course;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程编排输入
 * */
@Data
public class CourseLayoutInput {

	/**
	 * 岗位id
	 * */
	@NotBlank
	String resourceId;

	@ApiModelProperty(allowableValues = "knowledge, skill")
	@NotBlank
	String field;

	@NotNull
	List<String> courseIds;
}
