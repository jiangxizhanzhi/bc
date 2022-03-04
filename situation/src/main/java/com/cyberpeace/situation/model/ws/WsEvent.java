package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.LineStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsEvent implements Serializable {

	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	@ApiModelProperty("源节点id")
	private String sourceId;

	@ApiModelProperty("目的节点id")
	private String targetId;

	@ApiModelProperty("攻击类型")
	private Integer attackType;

}
