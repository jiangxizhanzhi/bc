package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.integratation.model.situation.PositionContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodePositionReq implements Serializable {

	@ApiModelProperty("环境配置方案ID")
	private String envId;

	@ApiModelProperty("节点坐标")
	private Map<String, PositionContext> position;

}
