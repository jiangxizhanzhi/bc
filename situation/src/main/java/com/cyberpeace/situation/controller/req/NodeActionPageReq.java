package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeActionPageReq extends NodePageReq implements Serializable {

	@ApiModelProperty("行为类型")
	private String actionType;

}
