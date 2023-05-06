package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏ID返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DashboardIdRes implements Serializable {

	/**
	 * 大屏ID
	 */
	@ApiModelProperty("大屏ID")
	private String dashboardId;

	/**
	 * 大屏类型
	 */
	@ApiModelProperty("大屏类型 1-定制大屏 2-自编大屏 3-三方靶标")
	private Integer dashboardType;

	/**
	 * 定制类型
	 */
	@ApiModelProperty("定制类型（定制大屏用）")
	private Integer customCode;

	/**
	 * 跳转链接
	 */
	@ApiModelProperty("跳转链接（三方靶标用）")
	private String thirdUrl;

	/**
	 * 跳转链接
	 */
	@ApiModelProperty("跳转链接")
	private Integer level;

	/**
	 * 任务类型
	 */
	@ApiModelProperty("任务类型")
	private String taskType;

	public DashboardIdRes() {}

	/**
	 * 构造函数
	 *
	 * @param dashboardId 大屏ID
	 * @param thirdUrl    跳转链接
	 */
	public DashboardIdRes(String dashboardId, String thirdUrl) {
		this.setDashboardId(dashboardId);
		this.dashboardType = 3;
		this.customCode = 1;
		this.level = 3;
		this.thirdUrl = thirdUrl;
	}
}
