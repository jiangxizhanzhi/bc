package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.integratation.model.situation.PositionContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodePositionReq implements Serializable {

	@ApiModelProperty("模板ID")
	private String templateId;

	@ApiModelProperty("类型 1-模板 2-实例")
	private Integer type;

	@ApiModelProperty("初始视角")
	private Map<String, PositionContext> position;

}
