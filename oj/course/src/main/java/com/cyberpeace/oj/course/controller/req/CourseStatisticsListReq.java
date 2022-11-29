package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CourseStatisticsListReq extends PageReqBase {


	@ApiModelProperty("方向")
	private String direction;

	@ApiModelProperty("课时类型")
	private Integer difficulty;

}
