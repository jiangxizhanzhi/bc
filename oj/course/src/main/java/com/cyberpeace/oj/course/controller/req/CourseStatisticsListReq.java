package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程统计学列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@ApiModel
@Getter
@Setter
public class CourseStatisticsListReq extends PageReqBase {


	/**
	 * 方向
	 *
	 * @see String
	 */
	@ApiModelProperty("方向")
	private String direction;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
	private Integer difficulty;

}
