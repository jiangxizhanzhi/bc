package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventTypeContext implements Serializable {

	@ApiModelProperty("编码")
	private String code;

	@ApiModelProperty("名称")
	private String name;

	public EventTypeContext() {}

	public EventTypeContext(String code, String name) {
		this.name = name;
		this.code = code;
	}

}
