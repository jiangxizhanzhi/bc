package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeIdPageReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("节点ID")
	private String nodeId;

	@ApiModelProperty("分页页码")
	private Integer page;

	@ApiModelProperty("每页条数")
	private Integer pageSize;

}
