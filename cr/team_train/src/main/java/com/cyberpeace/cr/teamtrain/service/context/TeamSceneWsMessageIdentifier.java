package com.cyberpeace.cr.teamtrain.service.context;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TeamSceneWsMessageIdentifier extends WsMessageIdentifier {

	private String businessId;
}
