package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 实操用户信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class OperationUserInfo implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String id;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户姓名")
	private String name;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名")
	private String username;

	/**
	 * 组织路径
	 *
	 * @see List<List<String>>
	 */
	@ApiModelProperty("组织信息")
	private List<List<String>> orgpaths;

}
