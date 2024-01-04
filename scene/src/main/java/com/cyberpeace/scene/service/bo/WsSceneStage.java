package com.cyberpeace.scene.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2024/1/4 9:51
 * @desc :
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsSceneStage implements Serializable {

    private String stage;

    private String stageName;

    private String icon;

    private Integer stagePercentage;
}
