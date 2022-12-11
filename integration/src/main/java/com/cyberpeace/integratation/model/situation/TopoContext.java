package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TopoContext implements Serializable {

	@ApiModelProperty("基础信息")
	private SceneContext baseInfo;

	@ApiModelProperty("元素信息")
	private Map<String, ElementContext> elements;

	@ApiModelProperty("连线信息")
	private List<ConnectionContext> connections;

}
