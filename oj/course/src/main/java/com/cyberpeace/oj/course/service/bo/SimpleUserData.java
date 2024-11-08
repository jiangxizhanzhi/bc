package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 简单用户数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class SimpleUserData implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 名称
	 */
	@ApiModelProperty("用户姓名")
	private String name;

	/**
	 * 组织id
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

}
