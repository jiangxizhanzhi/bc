package com.cyberpeace.situation.controller.res;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.situation.service.context.SceneConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigRes extends SceneConfigContext {

	@ApiModelProperty("态势数据刷新频率（秒/次）")
	private Integer refreshRate = Integer
			.parseInt(ConfigUtil.getContextProperty("situation.refresh.rate"));

	public SceneConfigRes(Boolean linkStatus, Boolean flowAnalysis, Boolean flowAutoRun) {
		this.setLinkStatus(linkStatus);
		this.setFlowAnalysis(flowAnalysis);
		this.setFlowAutoRun(flowAutoRun);
	}

	public SceneConfigRes() {}

}