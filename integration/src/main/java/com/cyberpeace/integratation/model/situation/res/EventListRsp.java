package com.cyberpeace.integratation.model.situation.res;

import java.util.List;

import com.cyberpeace.integratation.model.situation.ThirdDictContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventListRsp extends ThirdDictContext {

	@ApiModelProperty("编码")
	private List<ThirdDictContext> events;

	public EventListRsp() {}

	public EventListRsp(String code, String name) {
		this.setName(name);
		this.setCode(code);
	}

}
