package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置过滤请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationConfigFilterReq implements Serializable {

	/**
	 * 作用域id
	 */
	@ApiModelProperty(value = "作用域id（拓扑/场景）")
	private String scope;

	/**
	 * 元素id
	 */
	@ApiModelProperty(value = "元素id")
	private String nodeId;

	/**
	 * 元素名称
	 */
	@ApiModelProperty(value = "元素名称")
	private String nodeName;

	/**
	 * 元素类型 1-区域 2-节点
	 */
	@ApiModelProperty(value = "元素类型 1-区域 2-节点")
	private Integer nodeType;

	public SituationConfigFilterReq() {};

	/**
	 * 构造函数
	 *
	 * @param scope 作用域
	 */
	public SituationConfigFilterReq(String scope) {
		this.scope = scope;
	};

}
