package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowInfoContext implements Serializable {

	@ApiModelProperty(value = "流量id")
	private String flowId;

	@ApiModelProperty(value = "源ip")
	private String sourceIp;
}
