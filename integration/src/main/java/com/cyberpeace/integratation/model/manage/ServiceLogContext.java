package com.cyberpeace.integratation.model.manage;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceLogContext implements Serializable {

	@ApiModelProperty("日志内容")
	private String content;

	@ApiModelProperty("请求IP")
	private String ip;

	@ApiModelProperty("模块")
	private String module;

	@ApiModelProperty("子模块")
	private String subModule;

	@ApiModelProperty("类型")
	private String type;

	@ApiModelProperty("链接")
	private String url;

	@ApiModelProperty("用户ID")
	private String userId;

	@ApiModelProperty("用户名")
	private String userName;

	public ServiceLogContext(String serviceName) {}

	public ServiceLogContext() {}

}
