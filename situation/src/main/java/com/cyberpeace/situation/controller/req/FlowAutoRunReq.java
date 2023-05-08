package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量自动运行请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class FlowAutoRunReq implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty(value = "场景id", required = true)
	@NotEmpty
	private String sceneId;

	/**
	 * 流量交互类型
	 */
	@ApiModelProperty("流量交互类型：0 背景流量，1 攻击流量，2 全流量")
	private Integer interactiveType;

}
