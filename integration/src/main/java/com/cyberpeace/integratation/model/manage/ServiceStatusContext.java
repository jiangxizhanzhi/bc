package com.cyberpeace.integratation.model.manage;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class ServiceStatusContext implements Serializable {

	@ApiModelProperty("服务ID")
	private Integer id;

	@ApiModelProperty("服务名称")
	private String serviceName;

	@ApiModelProperty("服务项目")
	private String serviceReason;

	@ApiModelProperty("服务状态")
	private String serviceStatus = "正常";

	public ServiceStatusContext(Integer id, String serviceName) {
		this.id = id;
		this.serviceName = serviceName;
	}

	public ServiceStatusContext() {}

	public enum ServiceName {

		TXPTFXT(1, "图形平台分系统"),
		STGLFXT(4, "视图管理分系统");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private ServiceName(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
