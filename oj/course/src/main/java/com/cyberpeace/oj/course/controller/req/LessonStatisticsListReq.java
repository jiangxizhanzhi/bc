package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课统计学列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@ApiModel
@Data
public class LessonStatisticsListReq extends PageReqBase {

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程ID")
	private String courseId;

	/**
	 * 搜索
	 *
	 * @see String
	 */
	@ApiModelProperty("名称查询")
	private String search;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
	private Integer type;

}
