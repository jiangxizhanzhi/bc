package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class LessonStatisticsListReq extends PageReqBase {

	@ApiModelProperty("课程ID")
	private String courseId;

	@ApiModelProperty("名称查询")
	private String search;

	@ApiModelProperty("课时类型")
	private Integer type;

}
