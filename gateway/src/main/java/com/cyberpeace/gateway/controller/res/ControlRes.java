package com.cyberpeace.gateway.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ControlRes implements Serializable {

	@ApiModelProperty("模块ID")
	private String moduleId;

	@ApiModelProperty("执行结果 0-成功 1-失败")
	private String status;

	public ControlRes() {};

	public ControlRes(String moduleId, String status) {
		this.moduleId = moduleId;
		this.status = status;
	}

}
