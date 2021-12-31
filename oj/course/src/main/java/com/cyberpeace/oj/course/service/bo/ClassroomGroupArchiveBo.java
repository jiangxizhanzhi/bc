package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class ClassroomGroupArchiveBo implements Serializable {

	@ApiModelProperty("模板编号")
	private String resourceId;

	@ApiModelProperty("模板名称")
	private String name;

	@ApiModelProperty("课堂编号")
	private String classroomId;
}
