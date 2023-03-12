package com.cyberpeace.integratation.model.situation.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.EventAxisContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventAxisRsp implements Serializable {

	@ApiModelProperty("事件列表")
	private List<EventAxisContext> events;
}
