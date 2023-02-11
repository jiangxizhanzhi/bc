package com.cyberpeace.cr.redvsblue.dao.entity;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

@Data
public class RbWsMessageIdentifier extends WsMessageIdentifier {

	private String sceneId;
}
