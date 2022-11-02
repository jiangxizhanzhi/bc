package com.cyberpeace.bs.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TargetBo implements Serializable {

	@ApiModelProperty(value = "设备id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "IP")
	private String ip;

	@ApiModelProperty(value = "监控链接")
	private String monitorUrl;
}
