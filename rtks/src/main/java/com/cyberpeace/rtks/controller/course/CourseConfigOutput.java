package com.cyberpeace.rtks.controller.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseConfigOutput {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

}
