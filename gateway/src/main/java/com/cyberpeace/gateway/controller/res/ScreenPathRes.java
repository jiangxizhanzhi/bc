package com.cyberpeace.gateway.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScreenPathRes implements Serializable {

	@ApiModelProperty(value = "跳转路径（不带host）")
	private String path;
}
