package com.cyberpeace.iov.service.bo.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResourceCountBo {

	@ApiModelProperty(value = "场景数量")
	private Integer scene;

	@ApiModelProperty(value = "设备数量")
	private Integer device;

	@ApiModelProperty(value = "课程数量")
	private Integer course;

	@ApiModelProperty(value = "工具库数量")
	private Integer tool;
}
