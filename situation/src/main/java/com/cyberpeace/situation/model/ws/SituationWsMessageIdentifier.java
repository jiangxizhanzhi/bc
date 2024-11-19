package com.cyberpeace.situation.model.ws;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 态势WS消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Data
public class SituationWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	private String sceneId;

}
