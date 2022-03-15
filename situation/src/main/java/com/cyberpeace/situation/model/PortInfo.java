package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PortInfo implements Serializable {

	@ApiModelProperty("端口id")
	private String portId;

	@ApiModelProperty("端口名称")
	private String name;

	@ApiModelProperty("端口IP")
	private String ip;
}
