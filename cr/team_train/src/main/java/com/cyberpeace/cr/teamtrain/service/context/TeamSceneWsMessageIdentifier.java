package com.cyberpeace.cr.teamtrain.service.context;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

@Data
public class TeamSceneWsMessageIdentifier extends WsMessageIdentifier {

	private String business_id;
}
