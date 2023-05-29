package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionConfig implements Serializable {

	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	@ApiModelProperty(value = "任务id列表", required = true)
	private List<String> missions;
}
