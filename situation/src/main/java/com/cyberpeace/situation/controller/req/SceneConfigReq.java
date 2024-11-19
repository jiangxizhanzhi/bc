package com.cyberpeace.situation.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.situation.service.context.SceneConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class SceneConfigReq  {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	@NotNull
	private String sceneId;

	/**
	 * 是否为实例场景
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

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