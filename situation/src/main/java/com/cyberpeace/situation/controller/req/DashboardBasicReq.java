package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏基础请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DashboardBasicReq implements Serializable {

	/**
	 * 大屏ID
	 */
	@ApiModelProperty("大屏ID")
	@NotEmpty
	private String dashboardId;

	/**
	 * 特性ID
	 */
	@ApiModelProperty("特征ID")
	@NotEmpty
	private String featureId;
}
