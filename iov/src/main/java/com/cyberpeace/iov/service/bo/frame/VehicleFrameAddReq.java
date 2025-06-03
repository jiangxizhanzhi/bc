package com.cyberpeace.iov.service.bo.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleFrameAddReq {

	@ApiModelProperty(value = "车型")
	private Integer model;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "拓扑描述")
	private String description;

	@ApiModelProperty(value = "拓扑")
	private String topo;
}
