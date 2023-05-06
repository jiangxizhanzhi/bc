package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.integratation.model.situation.PlatformContext;
import com.cyberpeace.integratation.model.situation.PlatformLinkContext;
import com.cyberpeace.situation.model.ViewAngle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模板平台返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class TemplatePlatformRes implements Serializable {

	/**
	 * 平台列表
	 */
	@ApiModelProperty("平台列表")
	private List<PlatformContext> platformList = Lists.newArrayList();

	/**
	 * 平台连线
	 */
	@ApiModelProperty("平台连线")
	private List<PlatformLinkContext> platformLinkList = Lists.newArrayList();

	/**
	 * 初始视角
	 */
	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle;

	public TemplatePlatformRes() {

	}

	/**
	 * 构造函数
	 *
	 * @param viewAngle 视角
	 */
	public TemplatePlatformRes(ViewAngle viewAngle) {
		this.viewAngle = viewAngle;
	}

}
