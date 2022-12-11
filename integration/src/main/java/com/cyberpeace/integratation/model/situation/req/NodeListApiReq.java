package com.cyberpeace.integratation.model.situation.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeListApiReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty("节点ID列表")
	private List<String> nodeIds;

}
