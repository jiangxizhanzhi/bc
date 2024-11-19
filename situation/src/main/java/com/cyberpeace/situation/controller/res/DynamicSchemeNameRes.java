package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动态方案名称结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DynamicSchemeNameRes implements Serializable {

	/**
	 * 方案id
	 *
	 * @see String
	 */
	@ApiModelProperty("方案id")
	private String resourceId;

	/**
	 * 方案名称
	 *
	 * @see String
	 */
	@ApiModelProperty("方案名称")
	private String name;

}
