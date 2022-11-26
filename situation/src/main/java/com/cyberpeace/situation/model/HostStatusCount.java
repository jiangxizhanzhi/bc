package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HostStatusCount implements Serializable {

	@ApiModelProperty("状态")
	private String status;

	@ApiModelProperty("数量")
	private Integer times;

	public HostStatusCount(String status, Integer times) {
		this.status = status;
		this.times = times;
	}
}
