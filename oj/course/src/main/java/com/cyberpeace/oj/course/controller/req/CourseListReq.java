package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程列表请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@ApiModel
@Data
public class CourseListReq extends PageReqBase {

	/**
	 * 电子书
	 */
	@ApiModelProperty("类型 基础=0，攻击=2，防御=3")
	private Integer eBook;

	/**
	 * 方向
	 */
	@ApiModelProperty("方向")
	private String direction;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

}
