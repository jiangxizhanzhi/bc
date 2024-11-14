package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程管理Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("后台课程信息")
public class CourseAdminBo extends CourseWebBo {

	/**
	 * 授权访问类型
	 */
	@ApiModelProperty("授权访问类型")
    private Integer auth;

	/**
	 * 授权访问数量
	 */
	@ApiModelProperty("授权访问数量")
    private Integer authCount;

	/**
	 * 授权操作类型
	 */
	@ApiModelProperty("授权操作类型")
    private Integer share;

	/**
	 * 授权操作数量
	 */
	@ApiModelProperty("授权操作数量")
    private Integer shareCount;
}
