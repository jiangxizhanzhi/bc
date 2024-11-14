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
	@ApiModelProperty("岗位id")
	String resourceId;

	/**
	 * knowledge, skill
	 */
	@ApiModelProperty(allowableValues = "knowledge, skill")
	@NotBlank
	String field;

	/**
	 * 课程id列表
	 */
	@NotNull
	@ApiModelProperty("课程id列表")
	List<String> courseIds;
}
