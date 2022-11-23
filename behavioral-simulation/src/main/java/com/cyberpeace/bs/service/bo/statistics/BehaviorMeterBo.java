package com.cyberpeace.bs.service.bo.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BehaviorMeterBo implements Serializable {

	@ApiModelProperty(value = "")
	private String id;

	@ApiModelProperty(value = "")
	private String ip;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String nics;

	@ApiModelProperty(value = "")
	private String password;

	@ApiModelProperty(value = "")
	private String resourceId;

	@ApiModelProperty(value = "")
	private String server;

	@ApiModelProperty(value = "")
	private String token;

	@ApiModelProperty(value = "")
	private String type = "cpcloud_behavior";

	@ApiModelProperty(value = "")
	private String username;
}
