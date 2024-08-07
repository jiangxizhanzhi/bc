package com.cyberpeace.iov.service.bo.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleFrameDetailBo extends VehicleFrameListBo {

	@ApiModelProperty(value = "拓扑")
	private String topo;
}
