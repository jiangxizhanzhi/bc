package com.cyberpeace.rtks.controller.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseConfigOutput {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

}
