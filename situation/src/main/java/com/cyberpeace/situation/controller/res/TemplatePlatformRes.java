package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.PlatformContext;
import com.cyberpeace.situation.model.ViewAngle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TemplatePlatformRes implements Serializable {

	@ApiModelProperty("平台列表")
	private List<PlatformContext> platformList;

	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle;

}
