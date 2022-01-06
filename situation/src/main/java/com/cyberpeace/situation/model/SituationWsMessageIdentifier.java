package com.cyberpeace.situation.model;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

@Data
public class SituationWsMessageIdentifier extends WsMessageIdentifier {

    private String sceneId;

}
