package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户实例，用于创建仪表
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class UserInstanceAddBo implements Serializable {

	/**
	 * 用户模型id
	 */
	@ApiModelProperty(value = "用户模型id", required = true)
	private String userModelId;

	/**
	 * 行为变量配置
	 */
	@ApiModelProperty(value = "行为变量配置")
	private List<BehaviorConfig> behaviorConfig;
}
