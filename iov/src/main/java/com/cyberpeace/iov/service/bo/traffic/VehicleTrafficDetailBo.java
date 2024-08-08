package com.cyberpeace.iov.service.bo.traffic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleTrafficDetailBo extends VehicleTrafficListBo {

	@ApiModelProperty(value = "blf文件")
	private String blfFile;

	@ApiModelProperty(value = "blf文件路径")
	private String blfFilePath;

	@ApiModelProperty(value = "blf文件名称")
	private String blfFileName;


}
