package com.cyberpeace.cr.crbase.service.context.scene;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 场景ws标识
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CrSceneWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 业务id
	 */
	private String business_id;
}
