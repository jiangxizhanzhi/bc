package com.cyberpeace.evaluate.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimateInstanceBody implements Serializable {

	@ApiModelProperty("评估实例Id")
	String instanceId;

	@ApiModelProperty("评估实例名称")
	String instanceName;

	@ApiModelProperty("评估实例描述")
	String instanceDesc;
}
