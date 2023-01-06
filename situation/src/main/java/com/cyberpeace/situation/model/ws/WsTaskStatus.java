package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsTaskStatus implements Serializable {

	@ApiModelProperty("任务状态")
	private String value;

	public WsTaskStatus(String value) {
		this.value = value;
	}

}
