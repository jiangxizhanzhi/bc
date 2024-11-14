package com.cyberpeace.situation.controller.res;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.situation.service.context.SceneConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigRes {

	/**
	 * 态势数据刷新频率（秒/次）
	 */
	@ApiModelProperty("态势数据刷新频率（秒/次）")
	private Integer refreshRate = Integer
			.parseInt(ConfigUtil.getContextProperty("situation.refresh.rate"));

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

	public SceneConfigRes(Boolean linkStatus, Boolean flowAnalysis, Boolean flowAutoRun) {
		this.setLinkStatus(linkStatus);
		this.setFlowAnalysis(flowAnalysis);
		this.setFlowAutoRun(flowAutoRun);
	}

	public SceneConfigRes() {}

}