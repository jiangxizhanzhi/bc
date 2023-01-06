package com.cyberpeace.gateway.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScreenPathRes implements Serializable {

	@ApiModelProperty(value = "跳转路径（不带host）")
	private String path;

	@ApiModelProperty(value = "大屏ID")
	private String dashboardId;

	public ScreenPathRes(String dashboardId) {
		this.path = "/page/mg/admin/cr/admin/situation-release/edit/" + dashboardId;
		this.dashboardId = dashboardId;
	}

	public ScreenPathRes() {}
}
