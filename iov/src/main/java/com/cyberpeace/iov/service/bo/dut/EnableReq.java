package com.cyberpeace.iov.service.bo.dut;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EnableReq {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "启用或禁用")
	private Boolean enable;
}
