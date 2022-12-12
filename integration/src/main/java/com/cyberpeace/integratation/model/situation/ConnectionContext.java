package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConnectionContext implements Serializable {

	@ApiModelProperty("链路ID")
	private String connectionId;

	@ApiModelProperty("链路类型")
	private String connectionType;

	@ApiModelProperty("源节点ID")
	private String source;

	@ApiModelProperty("目标节点ID")
	private String target;

}
