package com.cyberpeace.iov.service.bo.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TargetCountBo {

	@ApiModelProperty(value = "实体靶标")
	private Integer entity;

	@ApiModelProperty(value = "虚拟靶标")
	private Integer virtual;
}
