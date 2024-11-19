package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势配置新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SituationConfigAddReq implements Serializable {

	/**
	 * 作用域（拓扑/场景）列表，默认空值作用于全局
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "作用域（拓扑/场景）列表，默认空值作用于全局", required = false)
	private List<String> scope;

	/**
	 * 元素id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "元素id", required = false)
	private String nodeId;

	/**
	 * 元素名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "元素名称", required = false)
	private String nodeName;

	/**
	 * 元素类型 1-区域 2-节点
	 *
	 * @see Integer
	 */
	@NotNull
	@ApiModelProperty(value = "元素类型 1-区域 2-节点", required = true)
	private Integer nodeType;

	/**
	 * 属性 1-层级
	 *
	 * @see Integer
	 */
	@NotNull
	@ApiModelProperty(value = "属性 1-层级", required = true)
	private Integer attribute;

	/**
	 * 属性值
	 *
	 * @see String
	 */
	@NotNull
	@ApiModelProperty(value = "属性值", required = true)
	private String attributeValue;
}
