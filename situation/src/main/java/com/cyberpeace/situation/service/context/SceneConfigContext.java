package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneConfigContext implements Serializable {

	/**
	 * 是否获取流量攻击事件
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否获取流量攻击事件")
	private Boolean flowAnalysis;

	/**
	 * 是否自动运行流量
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否自动运行流量")
	private Boolean flowAutoRun;

	/**
	 * 是否获取节点链路状态
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否获取节点链路状态")
	private Boolean linkStatus = true;

}
