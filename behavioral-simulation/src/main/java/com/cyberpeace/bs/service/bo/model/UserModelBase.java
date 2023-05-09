package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户模型基础
 * 用户行为模拟基础部分
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class UserModelBase implements Serializable {

	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	private String userName;

	/**
	 * 用户类型id
	 */
	@ApiModelProperty(value = "用户类型id")
	private String userTypeId;

	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "访问设备")
	private Integer accessOs;

	/**
	 * 访问配置
	 */
	@ApiModelProperty(value = "访问配置")
	private Integer accessConfig;

	/**
	 * 访问习惯
	 */
	@ApiModelProperty(value = "访问习惯，取出现频次最高的前3个")
	private List<String> accessHabit;
}
