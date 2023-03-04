package com.cyberpeace.situation.controller.res;

import java.util.List;

import com.cyberpeace.integratation.model.situation.ThirdDictContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventTypeRes extends ThirdDictContext {

	@ApiModelProperty("编码")
	private List<ThirdDictContext> subTypes;

	public EventTypeRes() {}

	public EventTypeRes(String code, String name) {
		this.setName(name);
		this.setCode(code);
	}

}
