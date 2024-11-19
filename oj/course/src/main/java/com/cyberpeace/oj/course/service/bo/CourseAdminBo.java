package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程管理Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see CourseWebBo
 * @since 1.0.0
 */
@Data
@ApiModel("后台课程信息")
public class CourseAdminBo extends CourseWebBo {

	/**
	 * 授权访问类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权访问类型")
    private Integer auth;

	/**
	 * 授权访问数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权访问数量")
    private Integer authCount;

	/**
	 * 授权操作类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权操作类型")
    private Integer share;

	/**
	 * 授权操作数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权操作数量")
    private Integer shareCount;
}
