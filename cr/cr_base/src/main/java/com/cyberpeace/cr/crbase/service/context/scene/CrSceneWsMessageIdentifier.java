package com.cyberpeace.cr.crbase.service.context.scene;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 场景ws标识
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Data
public class CrSceneWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String business_id;
}
