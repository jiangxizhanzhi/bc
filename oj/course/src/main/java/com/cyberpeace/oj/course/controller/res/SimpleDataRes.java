package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleDataRes implements Serializable {

	@ApiModelProperty("课程编号")
	private String resourceId;

	@ApiModelProperty("资源名称")
	private String name;

}
