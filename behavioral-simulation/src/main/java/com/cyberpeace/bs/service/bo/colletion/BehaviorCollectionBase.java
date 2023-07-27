package com.cyberpeace.bs.service.bo.colletion;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BehaviorCollectionBase implements Serializable {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "录制设备")
	private Integer accessOs;
}
