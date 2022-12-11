package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeIdApiReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("节点ID")
	private String nodeId;

}
