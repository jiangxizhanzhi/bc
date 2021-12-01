package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

@Data
public class OperationWsMessageIdentifier extends WsMessageIdentifier {

    /** 场景为共享时 sceneShare=true 非共享时 sceneShare=user_id+false*/
    private String sceneShare;

    private String practiceId;

    private String topoId;

}
