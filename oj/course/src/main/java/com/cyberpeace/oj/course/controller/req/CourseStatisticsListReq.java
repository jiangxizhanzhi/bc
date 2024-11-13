package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程统计学列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel
@Data
public class CourseStatisticsListReq extends PageReqBase {


	/**
	 * 方向
	 */
	@ApiModelProperty("方向")
	private String direction;

	/**
	 * 难度
	 */
	@ApiModelProperty("课时类型")
	private Integer difficulty;

}
