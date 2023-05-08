package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 二维组件过滤请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class TwoDimensionalComponentsFilterReq implements Serializable {

	/**
	 * 组件名称
	 */
	@ApiModelProperty(value = "组件名称")
	private String name;

	/**
	 * 图表类型
	 */
	@ApiModelProperty(value = "图表类型")
	private String type;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String desc;

}
