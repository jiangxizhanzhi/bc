package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class CourseListReq extends PageReqBase {

	@ApiModelProperty("类型 基础=0，攻击=2，防御=3")
	private Integer eBook;

	@ApiModelProperty("方向")
	private String direction;

	@ApiModelProperty("难度")
	private Integer difficulty;
	@ApiModelProperty("岗位")
	private Integer role;

}
