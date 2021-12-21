package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleCourseRes implements Serializable {

	@ApiModelProperty("课程编号")
	private String resourceId;

	@ApiModelProperty("课程名称")
	private String name;

}
