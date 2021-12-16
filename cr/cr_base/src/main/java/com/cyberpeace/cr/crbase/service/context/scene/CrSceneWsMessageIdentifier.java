package com.cyberpeace.cr.crbase.service.context.scene;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

@Data
public class CrSceneWsMessageIdentifier extends WsMessageIdentifier {

	private String business_id;
}
