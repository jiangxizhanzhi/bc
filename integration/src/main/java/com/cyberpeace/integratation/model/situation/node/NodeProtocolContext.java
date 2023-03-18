package com.cyberpeace.integratation.model.situation.node;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeProtocolContext implements Serializable {

	@ApiModelProperty("协议名称")
	private String name;

	@ApiModelProperty("是否在用")
	private Boolean inUse;

}
