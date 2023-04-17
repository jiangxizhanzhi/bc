package com.cyberpeace.situation.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LinkStateRes implements Serializable {

	@ApiModelProperty("链路状态")
	private String linkState;

	@ApiModelProperty("带宽")
	private Integer bandwidth;

	@ApiModelProperty("上行速率")
	private Integer upLinkRate;

	@ApiModelProperty("下行速率")
	private Integer downLinkRate;

	@ApiModelProperty("链路id")
	private String linkId;

	public LinkStateRes() {
		this.linkState = "up";
		this.bandwidth = 1000;
		this.downLinkRate = 0;
		this.upLinkRate = 0;
	}
}
