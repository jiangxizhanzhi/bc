package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TopoParaContext implements Serializable {

	@ApiModelProperty(value = "环境配置方案ID")
	private String envId;

	@ApiModelProperty(value = "所需层级")
	private Integer level = 3;

	@ApiModelProperty(value = "子网ID")
	private String networkId;

	@ApiModelProperty(value = "平台ID")
	private String platformId;

	public TopoParaContext() {}

	public TopoParaContext(String envId) {
		this.envId = envId;
	}

}
