package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 三维组件过滤请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class ThreeDimensionalComponentsFilterReq implements Serializable {

	/**
	 * 组件名称
	 */
	@ApiModelProperty(value = "组件名称")
	private String name;

	/**
	 * 图表类型
	 */
	@ApiModelProperty(value = "图表类型")
	private String threeDimensionalComponentsCategory;

}
