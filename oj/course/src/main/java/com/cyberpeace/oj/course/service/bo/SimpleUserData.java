package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 简单用户数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class SimpleUserData implements Serializable {

	/**
	 * 用户编号
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 用户姓名
	 *
	 * @see String
	 */
	@ApiModelProperty("用户姓名")
	private String name;

	/**
	 * 组织编号
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

}
