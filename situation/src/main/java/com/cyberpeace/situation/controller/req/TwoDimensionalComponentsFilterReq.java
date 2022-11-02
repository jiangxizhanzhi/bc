package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TwoDimensionalComponentsFilterReq implements Serializable {

	@ApiModelProperty(value = "组件名称")
	private String name;

	@ApiModelProperty(value = "图表类型")
	private String type;

	@ApiModelProperty(value = "描述")
	private String desc;

}
