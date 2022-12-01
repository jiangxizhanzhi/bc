package com.cyberpeace.situation.controller.res;

import java.util.List;

import com.cyberpeace.situation.service.context.EventTypeContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventTypeRes extends EventTypeContextRes {

	@ApiModelProperty("编码")
	private List<EventTypeContextRes> subTypes;

	public EventTypeRes() {}

	public EventTypeRes(String code, String name) {
		this.setName(name);
		this.setCode(code);
	}

}
