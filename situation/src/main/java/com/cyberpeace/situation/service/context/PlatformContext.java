package com.cyberpeace.situation.service.context;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlatformContext implements Serializable {

	@ApiModelProperty("平台ID")
	private String platformId;

	@ApiModelProperty("平台名称")
	private String platformName;

	@ApiModelProperty("平台模型资源地址")
	private String platformUrl;

	@ApiModelProperty("园区URL")
	private String parkUrl;

	@ApiModelProperty("设备列表")
	private List<String> nodeIds;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("高程（米）")
	private Integer altitude;

	public PlatformContext() {}

	public PlatformContext(String platformId, String platformName) {
		this.platformId = platformId;
		this.platformName = platformName;
	}

}
