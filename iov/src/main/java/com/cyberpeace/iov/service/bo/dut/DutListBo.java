package com.cyberpeace.iov.service.bo.dut;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DutListBo {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "ECU单元")
	private Integer ecu;

	@ApiModelProperty(value = "车型")
	private Integer model;

	@ApiModelProperty(value = "logo")
	private String logo;

	@ApiModelProperty(value = "域类型")
	private Integer area;

	@ApiModelProperty(value = "接口类型")
	private Integer interfaceType;

	@ApiModelProperty(value = "接口参数")
	private String interfaceParam;

	@ApiModelProperty(value = "状态, 1=在线，2=离线")
	private Integer status;
}
