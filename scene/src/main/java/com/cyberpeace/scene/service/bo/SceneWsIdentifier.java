package com.cyberpeace.scene.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/29 10:49
 * @desc :
 **/
@Data
public class SceneWsIdentifier extends WsMessageIdentifier {

    private String businessId;
}
