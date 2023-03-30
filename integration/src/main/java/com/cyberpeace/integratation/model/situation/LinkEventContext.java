package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LinkEventContext implements Serializable {

	@ApiModelProperty("连线ID")
	private String connectionId;

	@ApiModelProperty("连线状态 0-畅通 1-轻度拥挤 2-中度拥挤 3-重度拥挤 4-掉线")
	private Integer workStatus;

}
