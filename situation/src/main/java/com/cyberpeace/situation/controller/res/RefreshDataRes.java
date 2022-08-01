package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsNodeStatus;

import lombok.Data;

@Data
public class RefreshDataRes implements Serializable {

	private Boolean flowAutoRunResult;

	private WsMessage<WsNodeStatus> linkStatus;

	private List<WsMessage> flowEvents;

}