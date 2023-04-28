package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.integratation.model.situation.PlatformContext;
import com.cyberpeace.integratation.model.situation.PlatformLinkContext;
import com.cyberpeace.situation.model.ViewAngle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TemplatePlatformRes implements Serializable {

	@ApiModelProperty("平台列表")
	private List<PlatformContext> platformList = Lists.newArrayList();

	@ApiModelProperty("平台连线")
	private List<PlatformLinkContext> platformLinkList = Lists.newArrayList();

	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle;

	public TemplatePlatformRes() {

	}

	public TemplatePlatformRes(ViewAngle viewAngle) {
		this.viewAngle = viewAngle;
	}

}
