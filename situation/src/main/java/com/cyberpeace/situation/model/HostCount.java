package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HostCount implements Serializable {

	@ApiModelProperty("主机名称")
	private String hostName;

	@ApiModelProperty("次数")
	private Integer times;

	public HostCount(String hostName, Integer times) {
		this.hostName = hostName;
		this.times = times;
	}

	public HostCount() {
	}
}
