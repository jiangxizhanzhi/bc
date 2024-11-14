package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PortInfo implements Serializable {

	/**
	 * 端口id
	 */
	@ApiModelProperty("端口id")
	private String portId;

	/**
	 * 端口名称
	 */
	@ApiModelProperty("端口名称")
	private String name;

	/**
	 * 端口IP
	 */
	@ApiModelProperty("端口IP")
	private String ip;
}
