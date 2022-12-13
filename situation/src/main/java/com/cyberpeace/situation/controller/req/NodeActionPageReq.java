package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeActionPageReq extends NodePageReq implements Serializable {

	@ApiModelProperty(name = "action_type", value = "行为类型")
	private String actionType;

	public void setAction_type(String actionType) {
		this.actionType = actionType;
	}

}
