package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 元素类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class ElementType {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

	/**
	 * 资源名称
	 *
	 * @see String
	 */
	@ApiModelProperty("资源名称")
	private String name;

	public ElementType(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}
}
