package com.cyberpeace.situation.service.context.config;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardConfigContext implements Serializable {

	@ApiModelProperty(value = "通用配置")
	private CommonConfigContext commonConfig;

	@ApiModelProperty(value = "控件配置")
	private ComponentConfigContext componentConfig;

	@ApiModelProperty(value = "GIS配置")
	private GisConfigContext gisConfig;

	@ApiModelProperty(value = "拓扑配置")
	private TopoConfigContext topoConfig;

	@ApiModelProperty(value = "角色配置")
	private RoleConfigContext roleConfig;


}
