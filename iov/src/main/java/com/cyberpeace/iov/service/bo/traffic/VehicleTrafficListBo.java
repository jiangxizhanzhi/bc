package com.cyberpeace.iov.service.bo.traffic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleTrafficListBo {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "车型")
	private Integer model;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "拓扑描述")
	private String description;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

}
