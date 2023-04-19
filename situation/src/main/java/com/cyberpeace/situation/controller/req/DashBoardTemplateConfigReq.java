package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.context.config.GisConfigContext;
import com.cyberpeace.situation.service.context.config.TopoConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashBoardTemplateConfigReq implements Serializable {

	@ApiModelProperty("模板资源id")
	@NotBlank
	private String resourceId;

	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle;

	@ApiModelProperty(value = "GIS配置")
	private GisConfigContext gisConfig;

	@ApiModelProperty(value = "拓扑配置")
	private TopoConfigContext topoConfig;
}
