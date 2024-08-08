package com.cyberpeace.iov.service.bo.traffic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleTrafficModifyReq extends VehicleTrafficAddReq {

	@ApiModelProperty(value = "id")
	private String resourceId;
}
