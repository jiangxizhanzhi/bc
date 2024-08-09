package com.cyberpeace.iov.service.bo.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResourceCountBo {

	@ApiModelProperty(value = "场景数量")
	private Integer scene;

	@ApiModelProperty(value = "漏洞数量（/api/vuln/web/vuln/statistics/industry/）")
	private Integer vuln;

	@ApiModelProperty(value = "课程数量")
	private Integer course;

	@ApiModelProperty(value = "工具库数量")
	private Integer tool;

	@ApiModelProperty(value = "实体靶标")
	private Integer entity;

	@ApiModelProperty(value = "虚拟靶标")
	private Integer virtual;
}
