package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlatformContext implements Serializable {

	@ApiModelProperty("平台ID")
	private String platformId;

	@ApiModelProperty("平台名称")
	private String platformName;

	@ApiModelProperty("平台地址")
	private String address;

	@ApiModelProperty("平台模型ID")
	private String modelId = "";

	@ApiModelProperty("平台模型资源地址")
	private String platformUrl;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("高程（米）")
	private Integer altitude;

	@ApiModelProperty("归属角色")
	private String role;

	public PlatformContext() {}

	public PlatformContext(String platformId, String platformName) {
		this.platformId = platformId;
		this.platformName = platformName;
	}

}
