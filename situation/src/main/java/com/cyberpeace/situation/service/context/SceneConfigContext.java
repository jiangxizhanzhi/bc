package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigContext implements Serializable {

	/**
	 * 是否获取流量攻击事件
	 */
	@ApiModelProperty("是否获取流量攻击事件")
	private Boolean flowAnalysis;

	/**
	 * 是否自动运行流量
	 */
	@ApiModelProperty("是否自动运行流量")
	private Boolean flowAutoRun;

	/**
	 * 是否获取节点链路状态
	 */
	@ApiModelProperty("是否获取节点链路状态")
	private Boolean linkStatus = true;

}
