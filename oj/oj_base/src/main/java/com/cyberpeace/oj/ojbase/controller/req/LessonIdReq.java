package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LessonIdReq implements Serializable {

	@ApiModelProperty("课时编号")
	@NotBlank
	private String lessonId;

}
