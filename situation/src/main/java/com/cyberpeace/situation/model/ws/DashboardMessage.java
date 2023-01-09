package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardMessage implements Serializable {

	@ApiModelProperty("消息内容")
	private String content;

	@ApiModelProperty("持续时间")
	private Integer duration;

	public DashboardMessage(String content, Integer duration) {
		this.content = content;
		this.duration = duration;
	}

}
