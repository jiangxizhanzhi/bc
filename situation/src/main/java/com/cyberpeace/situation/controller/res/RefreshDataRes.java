package com.cyberpeace.situation.controller.res;

import java.util.List;

import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsNodeStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RefreshDataRes {

	@ApiModelProperty("是否自动运行流量")
	private Boolean flowAutoRunResult;

	@ApiModelProperty("节点和链路状态")
	private WsMessage<WsNodeStatus> linkStatus;

	@ApiModelProperty("流量事件")
	private List<WsMessage> flowEvents;

}