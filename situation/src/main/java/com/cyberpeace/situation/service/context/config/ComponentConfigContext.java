package com.cyberpeace.situation.service.context.config;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ComponentConfigContext implements Serializable {

	@ApiModelProperty(value = "标绘开关")
	private Boolean mark = false;

	@ApiModelProperty(value = "隐藏面板开关")
	private Boolean hide = false;

	@ApiModelProperty(value = "音效开关")
	private Boolean voice = false;

	@ApiModelProperty(value = "视角开关")
	private Boolean angle = false;

	@ApiModelProperty(value = "场景旋转开关")
	private Boolean rotate = false;

	@ApiModelProperty(value = "3D模式开关")
	private Boolean dimension = true;

	@ApiModelProperty(value = "动效开关")
	private EffectConfigContext effectConfig;

}
