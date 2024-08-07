package com.cyberpeace.iov.service.bo.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleFrameModifyReq extends VehicleFrameAddReq {

	@ApiModelProperty(value = "id")
	private String resourceId;
}
