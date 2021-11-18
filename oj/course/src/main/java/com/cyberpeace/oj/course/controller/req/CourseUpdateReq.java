package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class CourseUpdateReq extends CourseAddReq {

	@ApiModelProperty("课程资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

}
