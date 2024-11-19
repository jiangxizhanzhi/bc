package com.cyberpeace.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势配置过滤器请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SituationConfigFilterReq implements Serializable {

	/**
	 * 作用域id（拓扑/场景）
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "作用域id（拓扑/场景）")
	private String scope;

	/**
	 * 元素id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "元素id")
	private String nodeId;

	/**
	 * 元素名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "元素名称")
	private String nodeName;

	/**
	 * 元素类型 1-区域 2-节点
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "元素类型 1-区域 2-节点")
	private Integer nodeType;

	public SituationConfigFilterReq() {};

	public SituationConfigFilterReq(String scope) {
		this.scope = scope;
	};

}
