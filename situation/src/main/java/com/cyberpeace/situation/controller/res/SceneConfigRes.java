package com.cyberpeace.situation.controller.res;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.situation.service.context.SceneConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SceneConfigRes extends SceneConfigContext {

	/**
	 * 刷新率
	 */
	@ApiModelProperty("态势数据刷新频率（秒/次）")
	private Integer refreshRate = Integer
			.parseInt(ConfigUtil.getContextProperty("situation.refresh.rate"));

	/**
	 * 构造函数
	 *
	 * @param linkStatus   链路状态
	 * @param flowAnalysis 流量分析
	 * @param flowAutoRun  流量自动运行
	 */
	public SceneConfigRes(Boolean linkStatus, Boolean flowAnalysis, Boolean flowAutoRun) {
		this.setLinkStatus(linkStatus);
		this.setFlowAnalysis(flowAnalysis);
		this.setFlowAutoRun(flowAutoRun);
	}

	public SceneConfigRes() {}

}