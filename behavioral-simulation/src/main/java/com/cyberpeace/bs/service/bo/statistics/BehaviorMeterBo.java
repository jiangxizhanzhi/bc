package com.cyberpeace.bs.service.bo.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BehaviorMeterBo implements Serializable {

	@ApiModelProperty(value = "仪表节点id")
	private String id;

	@ApiModelProperty(value = "仪表节点ip")
	private String ip;

	@ApiModelProperty(value = "仪表节点名称")
	private String name;

	@ApiModelProperty(value = "")
	private String nics;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "仪表resourceId，用于启动")
	private String resourceId;

	@ApiModelProperty(value = "")
	private String server;

	@ApiModelProperty(value = "令牌")
	private String token;

	@ApiModelProperty(value = "")
	private String type = "cpcloud_behavior";

	@ApiModelProperty(value = "用户名")
	private String username;
}
