package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeActionReq extends NodeIdPageReq implements Serializable {

	@ApiModelProperty("行为类型")
	private String actionType;

}
