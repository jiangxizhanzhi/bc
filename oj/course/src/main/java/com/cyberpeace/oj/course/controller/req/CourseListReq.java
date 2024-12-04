package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@ApiModel
@Data
public class CourseListReq extends PageReqBase {

	/**
	 * 电子书
	 *
	 * @see Integer
	 */
	@ApiModelProperty("类型 基础=0，攻击=2，防御=3")
	private Integer eBook;

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
	private Integer difficulty;
	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	@ApiModelProperty("岗位")
	private Integer role;

}
