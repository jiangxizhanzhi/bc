package com.cyberpeace.cr.crbase.service.context.scene;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 场景ws标识
 */
@Data
public class CrSceneWsMessageIdentifier extends WsMessageIdentifier {

	private String businessId;
}
