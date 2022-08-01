package com.cyberpeace.situation.model.ws;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.service.context.ConnectionStatusContext;
import com.cyberpeace.situation.service.context.ElementStatusContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsNodeStatus implements Serializable {

	@ApiModelProperty("节点状态列表")
	private List<ElementStatusContext> elementStatus = Lists.newArrayList();

	@ApiModelProperty("链路状态")
	private List<ConnectionStatusContext> connectionStatus = Lists.newArrayList();

}
