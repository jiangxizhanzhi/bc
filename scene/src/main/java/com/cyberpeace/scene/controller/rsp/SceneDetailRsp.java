package com.cyberpeace.scene.controller.rsp;

import com.cyberpeace.scene.service.bo.SceneData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2024/1/3 9:57
 * @desc :
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SceneDetailRsp implements Serializable {

    private String topoId;

    private SceneData sceneData;
}
