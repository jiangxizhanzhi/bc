package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author SN
 * 用户行为模拟基础部分
 */
@Data
public class UserModelBase implements Serializable {

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "用户类型id")
	private String userTypeId;

	@ApiModelProperty(value = "访问设备")
	private Integer accessOs;

	@ApiModelProperty(value = "访问配置")
	private Integer accessConfig;

	@ApiModelProperty(value = "访问习惯，取出现频次最高的前3个")
	private List<String> accessHabit;
}
