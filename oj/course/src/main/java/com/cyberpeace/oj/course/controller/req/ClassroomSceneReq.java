package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class ClassroomSceneReq implements Serializable {

	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

}
