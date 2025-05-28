package com.cyberpeace.iov.service.bo.traffic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleTrafficAddReq {

	@ApiModelProperty(value = "车型")
	private String model;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "拓扑描述")
	private String description;

	@ApiModelProperty(value = "blf文件")
	private String blfFile;
}
