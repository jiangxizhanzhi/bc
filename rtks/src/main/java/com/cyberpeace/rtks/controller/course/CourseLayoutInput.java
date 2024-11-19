package com.cyberpeace.rtks.controller.course;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程编排输入
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class CourseLayoutInput {

	/**
	 * 岗位id
	 *
	 * @see String
	 */
	@NotBlank
	@ApiModelProperty("岗位id")
	String resourceId;

	/**
	 * knowledge, skill
	 *
	 * @see String
	 */
	@ApiModelProperty(allowableValues = "knowledge, skill")
	@NotBlank
	String field;

	/**
	 * 课程id列表
	 *
	 * @see List<String>
	 */
	@NotNull
	@ApiModelProperty("课程id列表")
	List<String> courseIds;
}
