package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.context.config.GisConfigContext;
import com.cyberpeace.situation.service.context.config.TopoConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏模板配置请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DashBoardTemplateConfigReq implements Serializable {

	/**
	 * 模板资源id
	 */
	@ApiModelProperty("模板资源id")
	@NotBlank
	private String resourceId;

	/**
	 * 初始视角
	 */
	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle;

	/**
	 * GIS配置
	 */
	@ApiModelProperty(value = "GIS配置")
	private GisConfigContext gisConfig;

	/**
	 * 拓扑配置
	 */
	@ApiModelProperty(value = "拓扑配置")
	private TopoConfigContext topoConfig;
}
