package com.cyberpeace.cr.crbase.service.context.scene;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Getter;
import lombok.Setter;

/**
 * 场景ws标识
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CrSceneWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 业务id
	 */
	private String business_id;
}
