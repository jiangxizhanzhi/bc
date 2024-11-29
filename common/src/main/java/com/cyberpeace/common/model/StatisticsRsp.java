package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class StatisticsRsp implements Serializable {

	@ApiModelProperty("taskStatusMap")
	private Map<String,Integer> taskStatusMap;

	@ApiModelProperty("nodeStatusMap")
	private Map<String,Integer> nodeStatusMap;
}
