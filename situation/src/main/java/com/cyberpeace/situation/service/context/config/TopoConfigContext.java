package com.cyberpeace.situation.service.context.config;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TopoConfigContext implements Serializable {

	@ApiModelProperty(value = "配置文件ID")
	private String configId;

}
