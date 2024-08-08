package com.cyberpeace.iov.service.bo.traffic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleTrafficDetailBo extends VehicleTrafficListBo {

	@ApiModelProperty(value = "blf文件")
	private String blfFile;
}
