package com.cyberpeace.situation.service.context.config;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommonConfigContext implements Serializable {

	@ApiModelProperty(value = "顶层页面 1-GIS 2-拓扑 3-双屏")
	private Integer topPage;

	@ApiModelProperty(value = "运行模式 1-正常 2-回放")
	private Integer runMode;

}
