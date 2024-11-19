package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量自动跑请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowAutoRunReq implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景id", required = true)
	@NotEmpty
	private String sceneId;

	/**
	 * 流量交互类型：0 背景流量，1 攻击流量，2 全流量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("流量交互类型：0 背景流量，1 攻击流量，2 全流量")
	private Integer interactiveType;

}
