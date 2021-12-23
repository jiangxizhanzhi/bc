package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LessonSceneReq implements Serializable {

	@ApiModelProperty("课时编号")
	@NotBlank
	private String resourceId;

}
