package com.cyberpeace.scene.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2024/1/4 9:46
 * @desc :
 **/
@Data
public class WsSceneData implements Serializable {

    private String sceneId;

    private WsScene scene;

    private String type;

    private TopologyElement sceneElement;
}
