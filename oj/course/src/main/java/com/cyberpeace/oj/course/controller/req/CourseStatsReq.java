package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程统计请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
@ApiModel
public class CourseStatsReq extends PageReqBase {
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
	@ApiModelProperty("难度")
    private Integer difficult;

}
