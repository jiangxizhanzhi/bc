package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MapSourceChangeReq implements Serializable {

	@ApiModelProperty("大屏ID")
	@NotEmpty
	private String dashboardId;

	@ApiModelProperty("特征ID")
	@NotEmpty
	private String featureId;

	@ApiModelProperty("地图源 1-谷歌 2-百度 3-高德")
	@NotEmpty
	private Integer code;
}
