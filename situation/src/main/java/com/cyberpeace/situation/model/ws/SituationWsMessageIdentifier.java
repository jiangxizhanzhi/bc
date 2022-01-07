package com.cyberpeace.situation.model.ws;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

@Data
public class SituationWsMessageIdentifier extends WsMessageIdentifier {

    private String sceneId;

}
