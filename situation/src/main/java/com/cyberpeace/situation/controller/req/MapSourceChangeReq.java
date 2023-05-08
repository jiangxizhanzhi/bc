package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 地图源切换请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class MapSourceChangeReq implements Serializable {

	/**
	 * 大屏ID
	 */
	@ApiModelProperty("大屏ID")
	@NotEmpty
	private String dashboardId;

	/**
	 * 特征ID
	 */
	@ApiModelProperty("特征ID")
	@NotEmpty
	private String featureId;

	/**
	 * 地图源 1-谷歌 2-百度 3-高德
	 */
	@ApiModelProperty("地图源 1-谷歌 2-百度 3-高德")
	private Integer code;
}
