package com.cyberpeace.iov.service.bo.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleFrameListBo {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "车型")
	private Integer model;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "拓扑描述")
	private String description;

	@ApiModelProperty(value = "创建者id")
	private String creator;

	@ApiModelProperty(value = "创建者名称")
	private String creatorName;

	@ApiModelProperty(value = "拓扑规模")
	private Integer scale;
}
