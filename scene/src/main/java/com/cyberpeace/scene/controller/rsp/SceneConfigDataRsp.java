package com.cyberpeace.scene.controller.rsp;

import com.cyberpeace.scene.dao.entity.TopologyEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2024/1/4 20:25
 * @desc :
 **/
@Data
public class SceneConfigDataRsp implements Serializable {

    private Integer authType;

    private String createUser;

    private String createUsername;

    private boolean enableRecording;

    private String id;

    private String logo;

    private String name;

    private String referenceMetaData;

    private Integer referenceType;

    private SceneConfigRsp sceneConfig;
}
