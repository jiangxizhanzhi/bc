package com.cyberpeace.scene.controller.rsp;

import com.cyberpeace.scene.dao.entity.SceneEntity;
import lombok.Data;

/**
 * @author :   SN
 * @date :   2024/1/5 13:46
 * @desc :
 **/
@Data
public class SceneDataRsp extends SceneEntity {

    private Integer event;

    private Integer eventStatus;
}
