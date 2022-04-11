package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceRole implements Serializable {

	@ApiModelProperty("靶标角色id")
	private Integer id;

	@ApiModelProperty("靶标角色名称")
	private String name;

	@ApiModelProperty("靶标子角色")
	private List<DeviceRole> child;

}
