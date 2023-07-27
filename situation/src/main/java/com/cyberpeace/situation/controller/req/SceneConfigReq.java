package com.cyberpeace.situation.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.situation.service.context.SceneConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigReq  {

	@ApiModelProperty("场景id")
	@NotNull
	private String sceneId;

	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

	@ApiModelProperty("是否获取流量攻击事件")
	private Boolean flowAnalysis;

	@ApiModelProperty("是否自动运行流量")
	private Boolean flowAutoRun;

	@ApiModelProperty("是否获取节点链路状态")
	private Boolean linkStatus = true;
}