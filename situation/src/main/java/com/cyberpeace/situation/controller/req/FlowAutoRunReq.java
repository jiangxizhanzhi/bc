package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowAutoRunReq implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty(value = "场景id", required = true)
	@NotEmpty
	private String sceneId;

	/**
	 * 流量交互类型：0 背景流量，1 攻击流量，2 全流量
	 */
	@ApiModelProperty("流量交互类型：0 背景流量，1 攻击流量，2 全流量")
	private Integer interactiveType;

}
