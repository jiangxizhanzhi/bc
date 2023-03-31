package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlatformLinkContext implements Serializable {

	@ApiModelProperty("源平台ID")
	private Integer linkId;

	@ApiModelProperty("源平台ID")
	private String sourcePlatformId;

	@ApiModelProperty("目标平台ID")
	private String targetPlatformId;

	public void setPolylineformId(Integer linkId) {
		this.linkId = linkId;
	}

	public void setSptid(String sourcePlatformId) {
		this.sourcePlatformId = sourcePlatformId;
	}

	public void setTptid(String targetPlatformId) {
		this.targetPlatformId = targetPlatformId;
	}

}
