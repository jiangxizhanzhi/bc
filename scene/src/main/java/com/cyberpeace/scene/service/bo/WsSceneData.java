package com.cyberpeace.scene.service.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2024/1/4 9:46
 * @desc :
 **/
@Data
public class WsSceneData implements Serializable {

    @JsonProperty("scene_id")
    private String sceneId;

    private WsScene scene;

    private String type;

    @JsonProperty("scene_element")
    private TopologyElement sceneElement;
}
