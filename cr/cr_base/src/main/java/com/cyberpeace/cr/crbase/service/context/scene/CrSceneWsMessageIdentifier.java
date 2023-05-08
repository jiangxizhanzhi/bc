package com.cyberpeace.cr.crbase.service.context.scene;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**cr场景ws消息标识符
 * 场景ws标识
 @author cyberpeace
 @date 2023/05/06
 */
@Data
public class CrSceneWsMessageIdentifier extends WsMessageIdentifier {

	private String business_id;
}
