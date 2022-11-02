package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ThreeDimensionalComponentsFilterReq implements Serializable {

	@ApiModelProperty(value = "组件名称")
	private String name;

	@ApiModelProperty(value = "图表类型")
	private String threeDimensionalComponentsCategory;

}
