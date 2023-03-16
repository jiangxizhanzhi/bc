package com.cyberpeace.situation.service.context.config;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EffectConfigContext implements Serializable {

	@ApiModelProperty(value = "攻击动效开关")
	private Boolean attackDynamicEffect = true;

	@ApiModelProperty(value = "防御动效开关")
	private Boolean defenceDynamicEffect = true;

	@ApiModelProperty(value = "背景动效开关")
	private Boolean backgroundDynamicEffect = true;

	@ApiModelProperty(value = "告警提示开关")
	private Boolean alarmPrompt = true;

}
