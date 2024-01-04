package com.cyberpeace.scene.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2024/1/4 16:43
 * @desc :
 **/
@Data
public class WsData implements Serializable {

    private WsSceneData data;

    private String type;
}
