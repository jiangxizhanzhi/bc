package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动效方案名称返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DynamicSchemeNameRes implements Serializable {

	/**
	 * 方案id
	 */
	@ApiModelProperty("方案id")
	private String resourceId;

	/**
	 * 方案名称
	 */
	@ApiModelProperty("方案名称")
	private String name;

}
