package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author SN
 * 用户行为模拟基础部分
 */
@Data
public class UserBehaviorBase implements Serializable {

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "用户类型")
	private Integer userType;

	@ApiModelProperty(value = "活跃度")
	private Integer activity;

	@ApiModelProperty(value = "访问配置")
	private Integer accessConfig;

	@ApiModelProperty(value = "访问习惯操作系统")
	private Integer accessOs;
}
