package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课时统计学列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see PageReqBase
 * @since 1.0.0
 */
@ApiModel
@Data
public class LessonStatisticsListReq extends PageReqBase {

	/**
	 * 课程ID
	 *
	 * @see String
	 */
	@ApiModelProperty("课程ID")
	private String courseId;

	/**
	 * 名称查询
	 *
	 * @see String
	 */
	@ApiModelProperty("名称查询")
	private String search;

	/**
	 * 课时类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
	private Integer type;

}
